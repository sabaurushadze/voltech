package com.tbc.selling.presentation.mapper.my_items

import com.tbc.core.presentation.mapper.category.toStringRes
import com.tbc.core.presentation.util.toPriceUsStyle
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.selling.presentation.model.my_items.UiMyItem

fun FeedItem.toPresentation() =
    UiMyItem(
        id = id,
        uid = uid,
        title = title,
        price = price.toPriceUsStyle(),
        category = category.toStringRes(),
        condition = condition.toStringRes(),
        location = location.toStringRes(),
        images = images
    )