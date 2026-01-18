package com.tbc.data.mapper

import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.tbc.domain.util.DataError
import kotlinx.coroutines.CancellationException

fun mapExceptionToSignInError(e: Exception): DataError.Auth {
    return when (e) {
        is FirebaseAuthUserCollisionException -> DataError.Auth.AccountAlreadyExists
        is CancellationException -> DataError.Auth.Cancelled
        else -> DataError.Auth.Unknown
    }
}