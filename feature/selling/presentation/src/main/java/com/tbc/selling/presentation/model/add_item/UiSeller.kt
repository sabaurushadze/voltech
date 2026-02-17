package com.tbc.selling.presentation.model.add_item

data class UiSellerRequest(
    val uid: String,
    val positive: Int,
    val neutral: Int,
    val negative: Int,
    val sellerPhotoUrl: String?,
    val sellerName: String?,
)