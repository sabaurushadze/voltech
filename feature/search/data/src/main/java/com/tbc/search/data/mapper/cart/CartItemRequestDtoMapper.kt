package com.tbc.search.data.mapper.cart

import com.tbc.search.data.dto.cart.CartItemRequestDto
import com.tbc.search.domain.model.cart.CartItemRequest

fun CartItemRequest.toData() =
    CartItemRequestDto(
        uid = uid,
        itemId = itemId,
        addedAt = addedAt
    )