package com.tbc.auth.domain.usecase.register

import javax.inject.Inject

class ValidateRegistrationPasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): Boolean {
        val regex = Regex("^(?=.*[0-9!@#\$%^&*])[A-Za-z0-9!@#\$%^&*]{8,}\$")
        return regex.matches(password)
    }
}