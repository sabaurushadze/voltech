package com.tbc.selling.domain.usecase.selling.my_items

import javax.inject.Inject

class CheckUserItemAmountUseCase @Inject constructor() {
    operator fun invoke(itemAmount: Int): Boolean {
        return itemAmount < MAX_ALLOWED_ITEMS
    }

    companion object {
        private const val MAX_ALLOWED_ITEMS = 5
    }
}