package com.tbc.domain.repository.register

import com.tbc.domain.util.DataError
import com.tbc.domain.util.Resource

interface RegisterRepository {
    suspend fun register(email: String, password: String): Resource<Unit, DataError.Auth>
}