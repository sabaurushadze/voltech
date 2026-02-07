package com.tbc.core.presentation.util

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.io.File

@Composable
fun rememberCameraLauncher(
    onImageCaptured: (Uri) -> Unit
): () -> Unit {
    val context = LocalContext.current

    val uri = remember {
        val file = createImageFile(context)
        androidx.core.content.FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                onImageCaptured(uri)
            }
        }

    return { launcher.launch(uri) }
}

fun createImageFile(context: Context): File {
    val timeStamp = System.currentTimeMillis()
    val storageDir = context.cacheDir
    return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
}