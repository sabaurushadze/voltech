package com.tbc.selling.domain.model

data class SellerRequest(
    val uid: String,
    val positive: Int,
    val neutral: Int,
    val negative: Int,
    val sellerPhotoUrl: String?,
    val sellerName: String?,
)

data class SellerResponse(
    val id: Int,
    val uid: String,
    val positive: Int,
    val neutral: Int,
    val negative: Int,
    val sellerPhotoUrl: String?,
    val sellerName: String?,
)