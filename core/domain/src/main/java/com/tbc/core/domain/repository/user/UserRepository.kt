package com.tbc.core.domain.repository.user

import com.tbc.core.domain.model.user.User

interface UserRepository {
    fun getCurrentUser(): User?
    fun signOut()
}