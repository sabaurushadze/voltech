package com.tbc.core.domain.usecase.user

import com.tbc.core.domain.model.user.User
import com.tbc.core.domain.repository.user.UserRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): User? {
        return userRepository.getCurrentUser()
    }
}