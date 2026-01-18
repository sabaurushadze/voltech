package com.tbc.domain.usecase.login

import com.tbc.domain.repository.login.LogInRepository
import com.tbc.domain.util.DataError
import com.tbc.domain.util.Resource
import javax.inject.Inject

class LogInWithEmailAndPasswordUseCase @Inject constructor(
    private val logInRepository: LogInRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<Unit, DataError.Auth> {
        return logInRepository.logIn(email = email, password = password)
    }
}