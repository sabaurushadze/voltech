package com.tbc.core.domain.usecase

import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.Resource
import com.tbc.core.domain.util.SessionError
import com.tbc.core.domain.util.asFailure
import com.tbc.core.domain.util.asSuccess
import javax.inject.Inject

class CheckUserAuthStateUseCase @Inject constructor(
    private val getCurrentUser: GetCurrentUserUseCase
) {
    operator fun invoke(): Resource<Unit, SessionError> {
        return if (getCurrentUser() != null) {
            Unit.asSuccess()
        } else {
            SessionError.Unauthenticated.asFailure()
        }
    }
}