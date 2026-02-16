package com.tbc.search.presentation.mapper.cart

import com.tbc.search.domain.model.cart.CartItemRequest
import com.tbc.search.presentation.model.cart.UiCartItemRequest
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun UiCartItemRequest.toDomain() =
    CartItemRequest(
        uid = uid,
        itemId = itemId,
        addedAt = Clock.System.now().toString()
    )

