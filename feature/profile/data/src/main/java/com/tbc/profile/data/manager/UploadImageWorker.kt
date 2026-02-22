package com.tbc.profile.data.manager

import android.content.Context
import android.net.Uri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.firebase.storage.FirebaseStorage
import com.tbc.profile.data.manager.FileUploadManagerKeys.RESULT_URL
import com.tbc.profile.data.manager.FileUploadManagerKeys.URI_KEY
import com.tbc.profile.data.util.ImageUtils
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.UUID

@HiltWorker
class UploadImageWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(appContext, workerParams) {

    private val storageRef = FirebaseStorage.getInstance().reference

    companion object {
        private const val MAX_RETRY_ATTEMPTS = 3
        private const val DEFAULT_THRESHOLD = 200 * 1024L
    }

    override suspend fun doWork(): Result {
        val uriString = inputData.getString(URI_KEY)

        if (uriString.isNullOrEmpty())
            return Result.failure()

        return try {
            val file = File(uriString)

            if (!file.exists()) {
                return Result.failure()
            }


            val extension = file.extension.ifEmpty { "jpg" }

            val fileRef = storageRef.child("${UUID.randomUUID()}.$extension")

            val fileUri = Uri.fromFile(file)

            ImageUtils.compressImage(applicationContext, fileUri, DEFAULT_THRESHOLD)
                ?.let { byteArray ->
                    fileRef.putBytes(byteArray).await()
                } ?: fileRef.putFile(fileUri).await()

            val resultUrl = fileRef.downloadUrl.await().toString()

            Result.success(workDataOf(RESULT_URL to resultUrl))
        } catch (e: Exception) {
            if (runAttemptCount < MAX_RETRY_ATTEMPTS) Result.retry()
            else Result.failure()
        }
    }
}