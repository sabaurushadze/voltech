package com.tbc.profile.presentation.mapper.watchlist

import com.tbc.core.presentation.util.toPriceUsStyle
import com.tbc.profile.presentation.model.watchlist.UiFavorite
import com.tbc.search.domain.model.feed.FeedItem

fun FeedItem.toPresentation(favoriteId: Int) =
    UiFavorite(
        id = id,
        favoriteId = favoriteId,
        title = title,
        price = price.toPriceUsStyle(),
        images = images,
        isSelected = false
    )