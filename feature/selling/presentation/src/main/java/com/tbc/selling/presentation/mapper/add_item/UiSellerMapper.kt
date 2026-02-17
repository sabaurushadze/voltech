package com.tbc.selling.presentation.mapper.add_item

import com.tbc.selling.domain.model.SellerRequest
import com.tbc.selling.presentation.model.add_item.UiSellerRequest

fun UiSellerRequest.toDomain() =
    SellerRequest(
        uid = uid,
        positive = positive,
        neutral = neutral,
        negative = negative,
        sellerPhotoUrl = sellerPhotoUrl,
        sellerName = sellerName
    )