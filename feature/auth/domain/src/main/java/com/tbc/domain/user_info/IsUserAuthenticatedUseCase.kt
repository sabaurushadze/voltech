package com.tbc.domain.user_info

import com.tbc.domain.repository.user_info.UserInfoRepository
import javax.inject.Inject

class IsUserAuthenticatedUseCase @Inject constructor(
    private val userRepository: UserInfoRepository
) {
    operator fun invoke(): Boolean {
        return userRepository.isUserAuthenticated()
    }
}