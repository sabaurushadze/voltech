package com.tbc.auth.domain.usecase

import javax.inject.Inject

class ValidatePasswordUseCase @Inject constructor() {
    operator fun invoke(password: String): Boolean {
        return password.isNotEmpty()
    }
}
