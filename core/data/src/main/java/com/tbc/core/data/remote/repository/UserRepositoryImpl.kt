package com.tbc.core.data.remote.repository

import com.google.firebase.auth.FirebaseAuth
import com.tbc.core.domain.model.user.User
import com.tbc.core.domain.repository.user.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : UserRepository {

    override fun getCurrentUser(): User? {
        return auth.currentUser?.run {
            User(
                uid = uid,
                name = displayName,
                photoUrl = photoUrl?.toString()
            )
        }
    }

    override fun signOut() {
        auth.signOut()
    }
}