package com.tbc.selling.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
internal data class SellerResponseDto(
    val id: Int,
    val uid: String,
    val positive: Int,
    val neutral: Int,
    val negative: Int,
    val sellerPhotoUrl: String?,
    val sellerName: String?,
)