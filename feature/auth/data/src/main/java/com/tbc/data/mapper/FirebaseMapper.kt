package com.tbc.data.mapper

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.tbc.domain.util.DataError
import kotlinx.coroutines.CancellationException

fun mapExceptionToSignInError(e: Exception): DataError.Auth {
    return when (e) {
        is FirebaseAuthWeakPasswordException -> DataError.Auth.WeakPassword
        is FirebaseAuthInvalidCredentialsException -> DataError.Auth.InvalidCredential
        is FirebaseAuthUserCollisionException -> DataError.Auth.AccountAlreadyExists
        is CancellationException -> DataError.Auth.Cancelled
        else -> DataError.Auth.Unknown
    }
}