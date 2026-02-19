package com.tbc.search.presentation.model.cart

data class UiCartItem (
    val id: Int,
    val cartId: Int,
    val title: String,
    val price: String,
    val images: List<String>,
    val sellerAvatar: String?,
    val sellerUserName: String?,
    val uid: String,
)