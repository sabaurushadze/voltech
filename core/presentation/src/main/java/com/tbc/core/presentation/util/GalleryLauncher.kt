package com.tbc.core.presentation.util

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun rememberGalleryLauncher(onImageSelected: (Uri) -> Unit): () -> Unit {
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { onImageSelected.invoke(it) }
        }

    return { galleryLauncher.launch("image/*") }
}

@Composable
fun rememberMultiGalleryLauncher(onImagesSelected: (List<Uri>) -> Unit): () -> Unit {
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uris ->
            if (uris.isNotEmpty()) {
                onImagesSelected(uris)
            }
        }

    return { galleryLauncher.launch("image/*") }
}
