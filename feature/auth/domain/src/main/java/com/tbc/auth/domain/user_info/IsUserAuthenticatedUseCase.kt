package com.tbc.auth.domain.user_info

import com.tbc.auth.domain.repository.user_info.UserInfoRepository
import javax.inject.Inject

class IsUserAuthenticatedUseCase @Inject constructor(
    private val userRepository: UserInfoRepository
) {
    operator fun invoke(): Boolean {
        return userRepository.isUserAuthenticated()
    }
}