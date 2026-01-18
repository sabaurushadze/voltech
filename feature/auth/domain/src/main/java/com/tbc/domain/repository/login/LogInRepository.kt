package com.tbc.domain.repository.login

import com.tbc.domain.util.DataError
import com.tbc.domain.util.Resource

interface LogInRepository {
    suspend fun logIn(email: String, password: String): Resource<Unit, DataError.Auth>
}