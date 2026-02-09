package com.tbc.search.presentation.mapper.feed

import com.tbc.core.presentation.mapper.category.toStringRes
import com.tbc.core.presentation.util.toPriceUsStyle
import com.tbc.core.presentation.util.toPriceUsStyle
import com.tbc.resource.R
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.model.feed.Location
import com.tbc.search.presentation.model.feed.UiFeedItem

fun FeedItem.toPresentation(): UiFeedItem {
    return UiFeedItem(
        id = id,
        title = title,
        categoryRes = category.toStringRes(),
        conditionRes = condition.toStringRes(),
        price = price.toPriceUsStyle(),
        images = images,
        quantity = quantity.toString(),
        locationRes = location.toStringRes(),
        userDescription = userDescription,
        sellerAvatar = sellerAvatar,
        sellerUserName = sellerUserName,
    )
}

fun Location.toStringRes(): Int {
    return when (this) {
        Location.DIDI_DIGHOMI -> R.string.didi_digohmi
        Location.GLDANI -> R.string.gldani
    }
}

fun Condition.toStringRes(): Int {
    return when (this) {
        Condition.NEW -> R.string.condition_new
        Condition.USED -> R.string.condition_used
        Condition.PARTS -> R.string.condition_parts
    }
}