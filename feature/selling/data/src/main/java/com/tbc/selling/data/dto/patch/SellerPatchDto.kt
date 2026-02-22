package com.tbc.selling.data.dto.patch

import kotlinx.serialization.Serializable

@Serializable
internal data class SellerProfilePatchDto(
    val sellerPhotoUrl: String?,
    val sellerName: String?,
)

@Serializable
internal data class SellerRatingPatchDto(
    val positive: Int,
    val neutral: Int,
    val negative: Int,
)