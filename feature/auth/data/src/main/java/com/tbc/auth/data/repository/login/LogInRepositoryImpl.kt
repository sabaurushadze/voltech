package com.tbc.auth.data.repository.login

import com.google.firebase.auth.FirebaseAuth
import com.tbc.auth.data.mapper.mapExceptionToSignInError
import com.tbc.auth.domain.repository.login.LogInRepository
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : LogInRepository {
    override suspend fun logIn(email: String, password: String): Resource<Unit, DataError.Auth> {
        return try {
            firebaseAuth
                .signInWithEmailAndPassword(email, password)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Failure(error = mapExceptionToSignInError(e))
        }
    }
}
