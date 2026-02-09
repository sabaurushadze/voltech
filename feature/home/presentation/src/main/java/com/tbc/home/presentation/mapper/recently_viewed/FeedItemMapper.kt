package com.tbc.home.presentation.mapper.recently_viewed

import com.tbc.core.presentation.util.toPriceUsStyle
import com.tbc.home.presentation.model.recently_viewed.UiRecentlyItem
import com.tbc.search.domain.model.feed.FeedItem

fun FeedItem.toPresentation() =
    UiRecentlyItem(
        id = id,
        title = title,
        price = price.toPriceUsStyle(),
        images = images
    )

fun List<FeedItem>.toPresentation() = this.map { it.toPresentation() }