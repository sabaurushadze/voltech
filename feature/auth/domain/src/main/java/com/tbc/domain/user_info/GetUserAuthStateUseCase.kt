package com.tbc.domain.user_info

import com.tbc.domain.repository.user_info.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserAuthStateUseCase @Inject constructor(
    private val userRepository: UserInfoRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return userRepository.getAuthState()
    }
}