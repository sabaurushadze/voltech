package com.tbc.search.presentation.mapper.seller_profile.seller_product

import com.tbc.core.presentation.util.toPriceUsStyle
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.presentation.model.seller_profile.seller_product.UiSellerProductItem

fun FeedItem.toPresentation() =
    UiSellerProductItem(
        id = id,
        title = title,
        price = price.toPriceUsStyle(),
        images = images
    )

fun List<FeedItem>.toPresentation() = this.map { it.toPresentation() }