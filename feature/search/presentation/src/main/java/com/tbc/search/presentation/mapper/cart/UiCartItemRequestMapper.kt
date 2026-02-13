package com.tbc.search.presentation.mapper.cart

import com.tbc.core.presentation.util.toIsoFormat
import com.tbc.search.domain.model.cart.CartItemRequest
import com.tbc.search.presentation.model.cart.UiCartItemRequest
import java.util.Date


fun UiCartItemRequest.toDomain() =
    CartItemRequest(
        uid = uid,
        itemId = itemId,
        addedAt = Date().toIsoFormat()
    )