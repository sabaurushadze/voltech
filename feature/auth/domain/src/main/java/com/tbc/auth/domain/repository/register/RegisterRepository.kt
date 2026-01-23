package com.tbc.auth.domain.repository.register

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource

interface RegisterRepository {
    suspend fun register(email: String, password: String): Resource<Unit, DataError.Auth>
}