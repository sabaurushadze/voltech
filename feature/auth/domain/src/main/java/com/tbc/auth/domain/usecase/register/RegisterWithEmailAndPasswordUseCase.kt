package com.tbc.auth.domain.usecase.register

import com.tbc.auth.domain.repository.register.RegisterRepository
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import javax.inject.Inject

class RegisterWithEmailAndPasswordUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<Unit, DataError.Auth> {
        return registerRepository.register(email = email, password = password)
    }
}