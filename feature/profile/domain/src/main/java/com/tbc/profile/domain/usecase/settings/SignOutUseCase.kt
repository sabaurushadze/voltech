package com.tbc.profile.domain.usecase.settings

import com.tbc.core.domain.repository.user.UserRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke() {
        return userRepository.signOut()
    }
}