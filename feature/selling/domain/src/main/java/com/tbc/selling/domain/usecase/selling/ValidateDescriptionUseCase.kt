package com.tbc.selling.domain.usecase.selling

import javax.inject.Inject

class ValidateDescriptionUseCase @Inject constructor() {
    operator fun invoke(description: String): Boolean {
        return description.length in MIN_DESCRIPTION_LENGTH..MAX_DESCRIPTION_LENGTH
    }

    companion object {
        private const val MIN_DESCRIPTION_LENGTH = 2
        private const val MAX_DESCRIPTION_LENGTH = 500
    }
}
