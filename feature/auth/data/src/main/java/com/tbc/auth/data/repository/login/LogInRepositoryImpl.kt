package com.tbc.auth.data.repository.login

import android.util.Log.d
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tbc.auth.data.mapper.mapExceptionToSignInError
import com.tbc.auth.domain.repository.login.LogInRepository
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LogInRepositoryImpl @Inject constructor() : LogInRepository {
    override suspend fun logIn(email: String, password: String): Resource<Unit, DataError.Auth> {
        return try {
            Firebase.auth
                .signInWithEmailAndPassword(email, password)
                .await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            d("asdd", "d: $e")
            Resource.Failure(error = mapExceptionToSignInError(e))
        }
    }
}
