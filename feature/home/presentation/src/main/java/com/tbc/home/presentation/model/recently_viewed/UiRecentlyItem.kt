package com.tbc.home.presentation.model.recently_viewed


data class UiRecentlyItem (
    val id: Int,
    val title: String,
    val price: String,
    val images: List<String>,
)