package com.tbc.search.presentation.mapper.cart

import com.tbc.selling.domain.model.SellerResponse

fun SellerResponse.toPresentation() =
    com.tbc.search.presentation.model.cart.UiSeller(
        name = sellerName,
        photoUrl = sellerPhotoUrl
    )