package com.tbc.profile.domain.usecase.edit_profile

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.profile.domain.repository.edit_profile.FileUploadManager
import javax.inject.Inject

class EnqueueFileUploadUseCase @Inject constructor(
    private val uploadManager: FileUploadManager,
) {
    suspend operator fun invoke(
        uri: String,
        userId: Int,
        userName: String?,
        ): Resource<String, DataError.Firestore> {
        return uploadManager.enqueueProfilePictureUpload(
            uri = uri,
            userId = userId,
            userName = userName,
        )
    }
}