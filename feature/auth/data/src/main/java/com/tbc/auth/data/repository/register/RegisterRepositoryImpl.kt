package com.tbc.auth.data.repository.register

import com.google.firebase.auth.FirebaseAuth
import com.tbc.auth.data.mapper.mapExceptionToSignInError
import com.tbc.auth.domain.repository.register.RegisterRepository
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : RegisterRepository {

    override suspend fun register(email: String, password: String): Resource<Unit, DataError.Auth> {
        return try {
            firebaseAuth
                .createUserWithEmailAndPassword(email, password)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(
                error = mapExceptionToSignInError(e)
            )
        }
    }
}