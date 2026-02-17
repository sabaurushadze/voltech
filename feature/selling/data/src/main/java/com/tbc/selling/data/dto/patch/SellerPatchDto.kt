package com.tbc.selling.data.dto.patch

import kotlinx.serialization.Serializable

@Serializable
data class SellerProfilePatchDto(
    val sellerPhotoUrl: String?,
    val sellerName: String?,
)

@Serializable
data class SellerRatingPatchDto(
    val positive: Int,
    val neutral: Int,
    val negative: Int,
)