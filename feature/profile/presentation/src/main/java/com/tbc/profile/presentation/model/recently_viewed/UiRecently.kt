package com.tbc.profile.presentation.model.recently_viewed

data class UiRecently(
    val id: Int,
    val recentlyId: Int,
    val title: String,
    val price: String,
    val images: List<String>,
    val isSelected: Boolean = false,
)