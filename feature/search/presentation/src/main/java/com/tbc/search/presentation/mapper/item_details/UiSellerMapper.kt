package com.tbc.search.presentation.mapper.item_details

import com.tbc.search.presentation.model.item_details.UiSeller
import com.tbc.selling.domain.model.SellerResponse


fun SellerResponse.toPresentation() =
    UiSeller(
        uid = uid,
        sellerName = sellerName,
        sellerPhotoUrl = sellerPhotoUrl,
        positive = positive,
        neutral = neutral,
        negative = negative
    )