package com.tbc.auth.domain.repository.login

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource

interface LogInRepository {
    suspend fun logIn(email: String, password: String): Resource<Unit, DataError.Auth>
}