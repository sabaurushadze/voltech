package com.tbc.search.presentation.model.feed

import androidx.annotation.StringRes

data class UiFeedItem(
    val id: Int,
    val title: String,
    @param:StringRes val categoryRes: Int,
    @param:StringRes val conditionRes: Int,
    val price: String,
    val image: String,
    val quantity: Int,
    @param:StringRes val locationRes: Int,
    val userDescription: String
)