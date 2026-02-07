package com.tbc.core.domain.usecase

import com.tbc.core.domain.model.user.User
import com.tbc.core.domain.repository.user.UserRepository
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core.domain.util.asSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val userRepository: UserRepository,
) {
    operator fun invoke(): User? {
        return userRepository.getCurrentUser()
    }
}
