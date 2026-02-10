package com.tbc.selling.domain.usecase.selling

import javax.inject.Inject

class ValidatePriceUseCase @Inject constructor() {
    operator fun invoke(price: String): Boolean {
        val value = price.toDoubleOrNull() ?: return false
        return value in MIN_PRICE..MAX_PRICE
    }

    companion object {
        private const val MIN_PRICE = 1.0
        private const val MAX_PRICE = 100_000_000.0
    }
}
