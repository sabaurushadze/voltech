package com.tbc.profile.domain.usecase.edit_profile

import com.tbc.profile.domain.repository.FileUploadManager
import javax.inject.Inject

class DeleteFileUseCase @Inject constructor(private val uploadManager: FileUploadManager) {
    operator fun invoke(url: String) = uploadManager.deleteFile(url)
}