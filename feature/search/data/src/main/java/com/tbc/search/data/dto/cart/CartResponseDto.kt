package com.tbc.search.data.dto.cart

import kotlinx.serialization.Serializable

@Serializable
data class CartResponseDto (
    val id: Int,
    val uid: String,
    val itemId: Int,
)