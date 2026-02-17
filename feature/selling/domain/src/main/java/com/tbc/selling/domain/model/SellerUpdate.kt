package com.tbc.selling.domain.model

data class SellerProfile(
    val id: Int,
    val sellerPhotoUrl: String?,
    val sellerName: String?,
)

data class SellerRating(
    val id: Int,
    val positive: Int,
    val neutral: Int,
    val negative: Int,
)