package com.tbc.search.domain.usecase.favorite

import com.tbc.core.domain.repository.user.UserRepository
import jdk.internal.net.http.common.Log
import javax.inject.Inject

class GetCurrentUserUidUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(): String? {
        return userRepository.getCurrentUser()?.uid
    }
}