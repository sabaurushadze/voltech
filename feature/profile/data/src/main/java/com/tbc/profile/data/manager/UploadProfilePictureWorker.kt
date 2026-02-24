package com.tbc.profile.data.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.firebase.storage.FirebaseStorage
import com.tbc.core.domain.util.onSuccess
import com.tbc.profile.data.manager.FileUploadManagerKeys.RESULT_URL
import com.tbc.profile.data.manager.FileUploadManagerKeys.URI_KEY
import com.tbc.profile.data.manager.FileUploadManagerKeys.USER_ID
import com.tbc.profile.data.manager.FileUploadManagerKeys.USER_NAME
import com.tbc.profile.data.util.ImageUtils
import com.tbc.profile.domain.usecase.edit_profile.UpdateProfilePictureUseCase
import com.tbc.resource.R
import com.tbc.selling.domain.model.SellerProfile
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.UpdateSellerProfileUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.tasks.await
import java.io.File
import java.util.UUID

@HiltWorker
class UploadProfilePictureWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val updateProfilePictureUseCase: UpdateProfilePictureUseCase,
    private val updateSellerProfileUseCase: UpdateSellerProfileUseCase,
) : CoroutineWorker(appContext, workerParams) {

    private val storageRef = FirebaseStorage.getInstance().reference

    companion object {
        private const val MAX_RETRY_ATTEMPTS = 3
        private const val DEFAULT_THRESHOLD = 200 * 1024L
        private const val SILENT_PROFILE_UPLOAD_CHANNEL = "silent_profile_upload_channel"

    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val channelId = SILENT_PROFILE_UPLOAD_CHANNEL
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Uploads",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = applicationContext.getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle("")
            .setContentText("")
            .setSmallIcon(R.drawable.ic_camera)
            .setOngoing(true)
            .build()

        return ForegroundInfo(1, notification)
    }

    override suspend fun doWork(): Result {
        val uriString = inputData.getString(URI_KEY)

        val userId = inputData.getInt(USER_ID, -1)
        val userName = inputData.getString(USER_NAME)

        if (uriString.isNullOrEmpty())
            return Result.failure()

        return try {
            val file = File(uriString)

            if (!file.exists()) {
                return Result.failure()
            }


            val extension = file.extension.ifEmpty { "jpg" }

            val fileRef = storageRef.child("users/${UUID.randomUUID()}.$extension")


            val fileUri = Uri.fromFile(file)

            ImageUtils.compressImage(applicationContext, fileUri, DEFAULT_THRESHOLD)
                ?.let { byteArray ->
                    fileRef.putBytes(byteArray).await()
                } ?: fileRef.putFile(fileUri).await()

            val resultUrl = fileRef.downloadUrl.await().toString()

            updateProfilePictureUseCase(resultUrl)
                .onSuccess {
                    val updatedUser = SellerProfile(
                        id = userId,
                        sellerPhotoUrl = resultUrl,
                        sellerName = userName
                    )
                    updateSellerProfileUseCase(updatedUser)
                }



            Result.success(workDataOf(RESULT_URL to resultUrl))
        } catch (_: Exception) {
            if (runAttemptCount < MAX_RETRY_ATTEMPTS) Result.retry()
            else Result.failure()
        }
    }
}