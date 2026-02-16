package com.tbc.search.data.mapper.cart

import com.tbc.search.data.dto.cart.CartResponseDto
import com.tbc.search.domain.model.cart.Cart

internal fun CartResponseDto.toDomain() =
    Cart(
        id = id,
        uid = uid,
        itemId = itemId
    )