package com.tbc.search.presentation.mapper.seller_profile.seller

import com.tbc.search.presentation.model.seller_profile.seller.UiSellerItem
import com.tbc.selling.domain.model.SellerResponse

fun SellerResponse.toPresentation() =
    UiSellerItem(
        sellerName = sellerName,
        sellerPhotoUrl = sellerPhotoUrl,
        positive = positive,
        neutral = neutral,
        negative = negative
    )

fun List<SellerResponse>.toPresentation() = this.map { it.toPresentation() }