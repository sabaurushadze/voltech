package com.tbc.search.presentation.model.feed

import androidx.annotation.StringRes

data class UiFeedItem(
    val id: Int,
    val title: String,
    @param:StringRes val categoryRes: Int,
    @param:StringRes val conditionRes: Int,
    val price: String,
    val images: List<String>,
    val quantity: String,
    @param:StringRes val locationRes: Int,
    val userDescription: String,
    val sellerAvatar: String?,
    val sellerUserName: String,
)