package com.tbc.search.presentation.model.item_details

data class UiSeller(
    val uid: String,
    val sellerName: String?,
    val sellerPhotoUrl: String?,
    val positive: Int,
    val neutral: Int,
    val negative: Int,
)