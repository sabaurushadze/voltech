package com.tbc.search.data.dto.cart

import kotlinx.serialization.Serializable

@Serializable
internal data class CartItemRequestDto(
    val uid: String,
    val itemId: Int,
    val addedAt: String
)