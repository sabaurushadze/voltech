package com.tbc.profile.domain.repository.edit_profile

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource

interface FileUploadManager {
    suspend fun enqueueProfilePictureUpload(
        uri: String,
        userId: Int?,
        userName: String?,
        ): Resource<String, DataError.Firestore>
    suspend fun enqueueSinglePhotoUpload(uri: String): Resource<String, DataError.Firestore>
    suspend fun enqueueMultipleFileUpload(uris: List<String>): Resource<List<String>, DataError.Firestore>
    suspend fun deleteFile(url: String): Resource<Unit, DataError.Firestore>
}