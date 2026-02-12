package com.tbc.search.domain.model.cart

data class CartItemRequest(
    val uid: String,
    val itemId: Int,
    val addedAt: String
)