package com.tbc.auth.domain.usecase

import javax.inject.Inject

class ValidateEmailUseCase @Inject constructor() {

    operator fun invoke(email: String): Boolean {
        return email.matches(VALIDATION_REGEX_EMAIL.toRegex())

    }

    companion object {
        private const val VALIDATION_REGEX_EMAIL =
            "^[A-Za-z0-9_%+\\-]+(?:\\.[A-Za-z0-9_%+\\-]+)*@[A-Za-z0-9]+(?:[.-][A-Za-z0-9]+)*\\.[A-Za-z]{2,}\$"
    }
}