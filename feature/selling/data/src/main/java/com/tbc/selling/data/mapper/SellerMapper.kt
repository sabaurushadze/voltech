package com.tbc.selling.data.mapper

import com.tbc.selling.data.dto.request.SellerRequestDto
import com.tbc.selling.data.dto.response.SellerResponseDto
import com.tbc.selling.domain.model.SellerRequest
import com.tbc.selling.domain.model.SellerResponse

internal fun SellerRequest.toDto() = SellerRequestDto(
    uid = uid,
    positive = positive,
    neutral = neutral,
    negative = negative,
    sellerPhotoUrl = sellerPhotoUrl,
    sellerName = sellerName
)

internal fun SellerResponseDto.toDomain() = SellerResponse(
    id = id,
    uid = uid,
    positive = positive,
    neutral = neutral,
    negative = negative,
    sellerPhotoUrl = sellerPhotoUrl,
    sellerName = sellerName,
)