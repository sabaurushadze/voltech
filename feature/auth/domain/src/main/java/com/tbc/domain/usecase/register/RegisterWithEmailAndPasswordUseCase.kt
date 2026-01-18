package com.tbc.domain.usecase.register

import com.tbc.domain.repository.register.RegisterRepository
import com.tbc.domain.util.DataError
import com.tbc.domain.util.Resource
import javax.inject.Inject

class RegisterWithEmailAndPasswordUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(email: String, password: String): Resource<Unit, DataError.Auth> {
        return registerRepository.register(email = email, password = password)
    }
}