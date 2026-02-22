package com.tbc.profile.data.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import androidx.exifinterface.media.ExifInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.InputStream
import kotlin.math.roundToInt

object ImageUtils {
    private const val QUALITY = 80

    suspend fun compressImage(
        context: Context,
        contentUri: Uri,
        compressionThreshold: Long,
    ): ByteArray? {
        return withContext(Dispatchers.IO) outer@{
            val mimeType = context.contentResolver.getType(contentUri)
            val inputBytes = context.contentResolver
                .openInputStream(contentUri)?.use { inputStream ->
                    inputStream.readBytes()
                } ?: return@outer null

            if (inputBytes.size <= compressionThreshold)
                return@outer inputBytes

            ensureActive()

            withContext(Dispatchers.Default) {
                var bitmap = BitmapFactory.decodeByteArray(inputBytes, 0, inputBytes.size)
                    ?: return@withContext null

                bitmap = rotateBitmapIfRequired(context, bitmap, contentUri)

                ensureActive()

                val compressFormat = when (mimeType) {
                    "image/png" -> Bitmap.CompressFormat.PNG
                    "image/jpeg" -> Bitmap.CompressFormat.JPEG
                    "image/webp" -> if (Build.VERSION.SDK_INT >= 30) Bitmap.CompressFormat.WEBP_LOSSLESS else Bitmap.CompressFormat.WEBP
                    else -> Bitmap.CompressFormat.JPEG
                }

                var outputBytes: ByteArray
                var quality = QUALITY

                do {
                    ByteArrayOutputStream().use { outputStream ->
                        bitmap.compress(compressFormat, quality, outputStream)
                        outputBytes = outputStream.toByteArray()
                        quality -= (quality * 0.1).roundToInt()
                    }
                } while (isActive && outputBytes.size > compressionThreshold && quality > 5 && compressFormat != Bitmap.CompressFormat.PNG)

                outputBytes
            }
        }
    }

    private fun rotateBitmapIfRequired(context: Context, bitmap: Bitmap, contentUri: Uri): Bitmap {
        val inputStream: InputStream? = context.contentResolver.openInputStream(contentUri)
        inputStream?.use {
            val exif = ExifInterface(it)
            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )

            val matrix = Matrix()
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
                else -> return bitmap
            }
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }
        return bitmap
    }
}
