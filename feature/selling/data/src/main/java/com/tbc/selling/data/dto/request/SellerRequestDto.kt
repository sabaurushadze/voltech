package com.tbc.selling.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
internal data class SellerRequestDto(
    val uid: String,
    val positive: Int,
    val neutral: Int,
    val negative: Int,
    val sellerPhotoUrl: String?,
    val sellerName: String?,
)