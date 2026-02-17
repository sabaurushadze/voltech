package com.tbc.profile.domain.usecase.edit_profile

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.profile.domain.repository.edit_profile.ProfileRepository
import javax.inject.Inject

class UpdateUserNameUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {
    suspend operator fun invoke(username: String): Resource<Unit, DataError.Firestore> {
        return profileRepository.updateUsername(username)
    }
}