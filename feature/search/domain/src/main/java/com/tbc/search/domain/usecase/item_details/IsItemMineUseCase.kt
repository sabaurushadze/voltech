package com.tbc.search.domain.usecase.item_details

import javax.inject.Inject

class IsItemMineUseCase @Inject constructor() {
    operator fun invoke(
        sellerUid: String,
        currentUid: String,
    ): Boolean {
        return sellerUid == currentUid

    }
}