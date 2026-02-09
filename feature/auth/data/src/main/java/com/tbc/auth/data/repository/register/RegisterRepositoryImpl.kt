package com.tbc.auth.data.repository.register

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tbc.auth.data.mapper.mapExceptionToSignInError
import com.tbc.auth.domain.repository.register.RegisterRepository
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor() : RegisterRepository {
    override suspend fun register(email: String, password: String): Resource<Unit, DataError.Auth> {
        return try {
            Firebase.auth
                .createUserWithEmailAndPassword(email, password)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(error = mapExceptionToSignInError(e))
        }
    }
}
