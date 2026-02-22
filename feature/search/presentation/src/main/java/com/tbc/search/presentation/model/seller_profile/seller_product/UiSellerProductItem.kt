package com.tbc.search.presentation.model.seller_profile.seller_product

data class UiSellerProductItem (
    val id: Int,
    val title: String,
    val price: String,
    val images: List<String>,
)