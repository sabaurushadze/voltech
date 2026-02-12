package com.tbc.search.presentation.mapper.cart

import com.tbc.core.presentation.util.toPriceUsStyle
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.presentation.model.cart.UiCartItem

fun FeedItem.toPresentation() =
    UiCartItem(
        id = id,
        title = title,
        price = price.toPriceUsStyle(),
        images = images,
        sellerAvatar = sellerPhotoUrl,
        sellerUserName = sellerName
    )

fun List<FeedItem>.toPresentation() = this.map { it.toPresentation() }