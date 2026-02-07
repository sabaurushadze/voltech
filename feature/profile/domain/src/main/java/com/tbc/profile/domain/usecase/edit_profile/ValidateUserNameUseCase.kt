package com.tbc.profile.domain.usecase.edit_profile

import javax.inject.Inject

class ValidateUserNameUseCase @Inject constructor() {
    operator fun invoke(username: String): Boolean {
        return username.length in 2..15
    }
}
