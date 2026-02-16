package com.tbc.profile.presentation.mapper.recently_viewed

import com.tbc.core.presentation.util.toPriceUsStyle
import com.tbc.profile.presentation.model.recently_viewed.UiRecently
import com.tbc.search.domain.model.feed.FeedItem

fun FeedItem.toPresentation(recentlyId: Int) =
    UiRecently(
        id = id,
        recentlyId = recentlyId,
        title = title,
        price = price.toPriceUsStyle(),
        images = images,
        isSelected = false
    )