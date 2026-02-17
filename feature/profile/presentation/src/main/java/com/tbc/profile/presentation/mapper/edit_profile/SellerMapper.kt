package com.tbc.profile.presentation.mapper.edit_profile

import com.tbc.profile.presentation.model.edit_profile.UiSeller
import com.tbc.selling.domain.model.SellerResponse

fun SellerResponse.toPresentation() =
    UiSeller(
        id = id,
        sellerName = sellerName,
        sellerPhotoUrl = sellerPhotoUrl,
    )