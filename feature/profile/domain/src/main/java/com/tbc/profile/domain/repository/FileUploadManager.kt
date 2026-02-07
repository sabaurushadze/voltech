package com.tbc.profile.domain.repository

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource

interface FileUploadManager {
    suspend fun enqueueFileUpload(uri: String): Resource<String, DataError.Firestore>
    suspend fun deleteFile(url: String): Resource<Unit, DataError.Firestore>
}