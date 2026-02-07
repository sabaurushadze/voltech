package com.tbc.profile.domain.usecase.edit_profile

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.profile.domain.repository.ProfileRepository
import javax.inject.Inject

class UpdateProfilePictureUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {
    suspend operator fun invoke(uri: String?): Resource<Unit, DataError.Firestore> {
        return profileRepository.updateProfilePhoto(uri)
    }
}