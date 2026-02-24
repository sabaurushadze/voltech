package com.tbc.core.domain.util

interface Error

sealed interface SessionError : Error {
    data object Unauthenticated : SessionError
}

sealed interface DataError : Error {
    enum class Network : DataError {
        NO_CONNECTION, TIMEOUT, BAD_REQUEST, UNAUTHORIZED, FORBIDDEN, NOT_FOUND, INTERNAL_SERVER_ERROR, SERVICE_UNAVAILABLE, UNKNOWN
    }

    sealed interface Auth : DataError {
        data object Unauthenticated : Auth
        data object InvalidCredential : Auth
        data object AccountAlreadyExists : Auth
        data object WeakPassword : Auth
        data object Cancelled : Auth
        data object Unknown : Auth
    }

    sealed interface Firestore : DataError {
        data object Unauthenticated : Firestore
        data object Unknown : Firestore
    }
}