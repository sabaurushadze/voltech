package com.tbc.core.presentation.mapper

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Error
import com.tbc.core.domain.util.ValidationError
import com.tbc.resource.R


fun Error.toStringResId(): Int = when (this) {
    is DataError -> this.toStringResId()
    is ValidationError -> this.toStringResId()
    else -> R.string.something_went_wrong_please_try_again
}

private fun DataError.toStringResId(): Int = when (this) {
    is DataError.Network -> this.toStringResId()
    is DataError.Auth -> this.toStringResId()
    is DataError.Firestore -> this.toStringResId()
}

private fun ValidationError.toStringResId(): Int = when (this) {
    is ValidationError.Email -> this.toStringResId()
    is ValidationError.Password -> this.toStringResId()
}

private fun DataError.Network.toStringResId(): Int = when (this) {
    DataError.Network.NO_CONNECTION -> R.string.no_internet_connection
    DataError.Network.TIMEOUT -> R.string.error_timeout
    DataError.Network.UNAUTHORIZED -> R.string.error_unauthorized
    DataError.Network.FORBIDDEN -> R.string.error_forbidden
    DataError.Network.NOT_FOUND -> R.string.error_not_found
    else -> R.string.something_went_wrong_please_try_again
}

private fun DataError.Auth.toStringResId(): Int = when (this) {
    DataError.Auth.InvalidCredential -> R.string.email_or_password_is_incorrect
    DataError.Auth.WeakPassword -> R.string.password_too_weak
    DataError.Auth.Unauthenticated -> R.string.error_unauthenticated
    DataError.Auth.AccountAlreadyExists -> R.string.error_permission_denied
    else -> R.string.something_went_wrong_please_try_again
}

private fun DataError.Firestore.toStringResId(): Int = when (this) {
    DataError.Firestore.DocumentNotFound -> R.string.error_document_not_found
    DataError.Firestore.PermissionDenied -> R.string.error_permission_denied
    DataError.Firestore.Unauthenticated -> R.string.error_unauthenticated
    else -> R.string.something_went_wrong_please_try_again
}

private fun ValidationError.Email.toStringResId(): Int = when (this) {
    ValidationError.Email.EMPTY -> R.string.error_email_field_empty
    ValidationError.Email.FORMAT -> R.string.error_wrong_email_format
}

private fun ValidationError.Password.toStringResId(): Int = when (this) {
    ValidationError.Password.EMPTY -> R.string.error_password_field_empty
    ValidationError.Password.TOO_WEAK -> R.string.password_too_weak
}