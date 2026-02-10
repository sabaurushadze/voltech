package com.tbc.selling.presentation.model.my_items

data class UiMyItem(
    val id: Int,
    val uid: String?,
    val title: String,
    val price: Double,
    val images: List<String>,
)
