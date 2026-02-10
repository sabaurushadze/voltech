package com.tbc.search.domain.model.feed

data class Item(
    val uid: String,
    val title: String,
    val category: String,
    val condition: String,
    val price: Double,
    val images: List<String>,
    val quantity: Int,
    val location: String,
    val userDescription: String,
    val sellerAvatar: String?,
    val sellerUserName: String?,
)
