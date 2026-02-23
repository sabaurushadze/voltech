package com.tbc.auth.data.repository.user_info

import com.google.firebase.auth.FirebaseAuth
import com.tbc.auth.domain.repository.user_info.UserInfoRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject


class UserInfoRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : UserInfoRepository {
    override fun isUserAuthenticated(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun getAuthState(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        firebaseAuth.addAuthStateListener(authStateListener)
        awaitClose {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }
}

