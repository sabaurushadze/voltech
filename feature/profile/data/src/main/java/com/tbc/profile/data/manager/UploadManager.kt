package com.tbc.profile.data.manager

import android.content.Context
import androidx.core.net.toUri
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.firebase.storage.FirebaseStorage
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.profile.data.manager.FileUploadManagerKeys.RESULT_URL
import com.tbc.profile.data.manager.FileUploadManagerKeys.URI_KEY
import com.tbc.profile.data.manager.FileUploadManagerKeys.USER_ID
import com.tbc.profile.data.manager.FileUploadManagerKeys.USER_NAME
import com.tbc.profile.domain.repository.edit_profile.FileUploadManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID
import javax.inject.Inject

internal object FileUploadManagerKeys {
    const val URI_KEY = "content_uri"
    const val USER_ID = "user_id"
    const val USER_NAME = "user_name"
    const val RESULT_URL = "result_url"
}

class UploadManager @Inject constructor(
    @param:ApplicationContext private val appContext: Context,
) : FileUploadManager {

    private val storage = FirebaseStorage.getInstance()

    override suspend fun enqueueProfilePictureUpload(
        uri: String,
        userId: Int?,
        userName: String?,
    ): Resource<String, DataError.Firestore> {
        val localPath = copyToInternalStorage(uri)
            ?: return Resource.Failure(DataError.Firestore.Unknown)

        val inputData = workDataOf(
            URI_KEY to localPath,
            USER_ID to userId,
            USER_NAME to userName,
        )

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<UploadProfilePictureWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()


        val workManager = WorkManager.getInstance(appContext)
        workManager.enqueue(workRequest)

        val finishedWorkInfo = workManager.getWorkInfoByIdFlow(workRequest.id)
            .filterNotNull()
            .first { it.state.isFinished }

        return handleUploadUpdates(finishedWorkInfo)
    }

    override suspend fun enqueueSinglePhotoUpload(uri: String): Resource<String, DataError.Firestore> {
        val localPath = copyToInternalStorage(uri)
            ?: return Resource.Failure(DataError.Firestore.Unknown)

        val inputData = workDataOf(
            URI_KEY to localPath,
        )

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<UploadImageWorker>()
            .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()


        val workManager = WorkManager.getInstance(appContext)
        workManager.enqueue(workRequest)

        val finishedWorkInfo = workManager.getWorkInfoByIdFlow(workRequest.id)
            .filterNotNull()
            .first { it.state.isFinished }

        return handleUploadUpdates(finishedWorkInfo)
    }

    override suspend fun enqueueMultipleFileUpload(
        uris: List<String>,
    ): Resource<List<String>, DataError.Firestore> = coroutineScope {
        val deferredResults = uris.map { uri ->
            async {
                enqueueSinglePhotoUpload(uri)
            }
        }

        val results = deferredResults.awaitAll()

        val successes = results.mapNotNull { (it as? Resource.Success)?.data }
        val failures = results.mapNotNull { (it as? Resource.Failure)?.error }

        if (failures.isNotEmpty()) {
            Resource.Failure(failures.first())
        } else {
            Resource.Success(successes)
        }
    }

    override suspend fun deleteFile(url: String): Resource<Unit, DataError.Firestore> {
        return try {
            storage
                .getReferenceFromUrl(url)
                .delete()
                .await()

            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(DataError.Firestore.Unknown)
        }
    }

    private fun handleUploadUpdates(workInfo: WorkInfo): Resource<String, DataError.Firestore> {
        return when (workInfo.state) {
            WorkInfo.State.SUCCEEDED -> {
                workInfo.outputData.getString(RESULT_URL)
                    ?.let { url -> Resource.Success(data = url) }
                    ?: Resource.Failure(error = DataError.Firestore.Unknown)
            }

            else -> Resource.Failure(error = DataError.Firestore.Unknown)
        }
    }

    private suspend fun copyToInternalStorage(uriString: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val uri = uriString.toUri()
                val inputStream = appContext.contentResolver.openInputStream(uri)
                    ?: return@withContext null

                val file = File(
                    appContext.filesDir,
                    "upload_${UUID.randomUUID()}.jpg"
                )

                inputStream.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }

                file.absolutePath
            } catch (e: Exception) {
                null
            }
        }
    }
}