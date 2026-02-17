package com.tbc.selling.data.mapper

import com.tbc.selling.data.dto.patch.SellerProfilePatchDto
import com.tbc.selling.data.dto.patch.SellerRatingPatchDto
import com.tbc.selling.domain.model.SellerProfile
import com.tbc.selling.domain.model.SellerRating

fun SellerProfile.toDto() = SellerProfilePatchDto(
    sellerPhotoUrl = sellerPhotoUrl,
    sellerName = sellerName
)

fun SellerRating.toDto() = SellerRatingPatchDto(
    positive = positive,
    neutral = neutral,
    negative = negative
)