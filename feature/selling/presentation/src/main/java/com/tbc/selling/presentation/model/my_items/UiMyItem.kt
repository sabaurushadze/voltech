package com.tbc.selling.presentation.model.my_items

import androidx.annotation.StringRes

data class UiMyItem(
    val id: Int,
    val uid: String?,
    val title: String,
    val price: String,
    @param:StringRes val category: Int,
    @param:StringRes val condition: Int,
    @param:StringRes val location: Int,
    val images: List<String>,
    val isSelected: Boolean = false,
)