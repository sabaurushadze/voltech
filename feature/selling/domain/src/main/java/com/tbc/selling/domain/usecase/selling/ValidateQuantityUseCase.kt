package com.tbc.selling.domain.usecase.selling

import javax.inject.Inject

class ValidateQuantityUseCase @Inject constructor() {
    operator fun invoke(quantity: String): Boolean {
        return quantity.toIntOrNull() in MIN_QUANTITY..MAX_QUANTITY
    }

    companion object {
        private const val MIN_QUANTITY = 1
        private const val MAX_QUANTITY = 10_000
    }
}
