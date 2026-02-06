package com.tbc.profile.data.manager

import android.content.Context
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
import com.tbc.profile.domain.repository.FileUploadManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import javax.inject.Inject

object FileUploadManagerKeys {
    const val URI_KEY = "content_uri"
    const val RESULT_URL = "result_url"
}

class UploadManager @Inject constructor(
    @param:ApplicationContext private val appContext: Context
) : FileUploadManager {

    private val storage = FirebaseStorage.getInstance()

    override suspend fun enqueueFileUpload(uri: String): Resource<String, DataError.Firestore> {
        val inputData = workDataOf(URI_KEY to uri)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<UploadWorker>()
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

    override fun deleteFile(url: String) {
        storage.getReferenceFromUrl(url).delete()
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
}