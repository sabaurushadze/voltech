package com.tbc.search.presentation.mapper.item_details

import com.tbc.search.presentation.model.item_details.UiSeller
import com.tbc.selling.domain.model.SellerRating
import com.tbc.selling.domain.model.SellerResponse


fun SellerResponse.toPresentation() =
    UiSeller(
        id = id,
        uid = uid,
        sellerName = sellerName,
        sellerPhotoUrl = sellerPhotoUrl,
        positive = positive,
        neutral = neutral,
        negative = negative,
    )

fun UiSeller.toDomain() =
    SellerRating(
        id = id,
        positive = positive,
        neutral = neutral,
        negative = negative
    )