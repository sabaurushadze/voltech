package com.tbc.search.presentation.model.feed

import androidx.annotation.StringRes

data class UiFeedItem(
    val id: Int,
    val uid: String,
    val title: String,
    @param:StringRes val categoryRes: Int,
    @param:StringRes val conditionRes: Int,
    val price: String,
    val images: List<String>,
    val quantity: String,
    @param:StringRes val locationRes: Int,
    val userDescription: String,
    val sellerName: String?,
    val sellerPhotoUrl: String?,
    val active: Boolean,
)