package com.tbc.search.presentation.mapper.cart

import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.presentation.model.cart.UiCartItem

fun FeedItem.toPresentation() =
    UiCartItem(
        id = id,
        title = title,
        price = price,
        images = images,
        sellerAvatar = sellerAvatar,
        sellerUserName = sellerUserName
    )

fun List<FeedItem>.toPresentation() = this.map { it.toPresentation() }