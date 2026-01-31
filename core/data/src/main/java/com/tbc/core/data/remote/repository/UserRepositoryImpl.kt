package com.tbc.core.data.remote.repository

import com.google.firebase.auth.FirebaseAuth
import com.tbc.core.domain.model.user.User
import com.tbc.core.domain.repository.user.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : UserRepository {
    override fun getCurrentUser(): User? {
        return auth.currentUser?.run { User(uid = uid) }
    }

    override fun signOut() {
        auth.signOut()
    }
}