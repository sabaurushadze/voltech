package com.tbc.selling.presentation.mapper.my_items

import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.selling.presentation.model.my_items.UiMyItem

fun FeedItem.toPresentation() =
    UiMyItem(
        id = id,
        uid = uid,
        title = title,
        price = price,
        images = images
    )