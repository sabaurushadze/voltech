package com.tbc.profile.domain.usecase.edit_profile

import com.tbc.profile.domain.repository.FileUploadManager
import javax.inject.Inject

class EnqueueMultipleFileUploadUseCase @Inject constructor(
    private val uploadManager: FileUploadManager
) {
    suspend operator fun invoke(uris: List<String>) = uploadManager.enqueueMultipleFileUpload(uris)
}