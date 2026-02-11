package com.tbc.search.presentation.model.cart

data class UiCartItem (
    val id: Int,
    val title: String,
    val price: Double,
    val images: List<String>,
    val sellerAvatar: String?,
    val sellerUserName: String,
)