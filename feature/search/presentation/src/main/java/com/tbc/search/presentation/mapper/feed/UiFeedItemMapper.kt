package com.tbc.search.presentation.mapper.feed

import com.tbc.search.domain.model.feed.Category
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.model.feed.Location
import com.tbc.search.presentation.model.feed.UiCategory
import com.tbc.search.presentation.model.feed.UiCondition
import com.tbc.search.presentation.model.feed.UiFeedItem
import com.tbc.search.presentation.model.feed.UiLocation

fun FeedItem.toPresentation(): UiFeedItem {
    return UiFeedItem(
        id = id,
        title = title,
        category = category.toPresentation(),
        condition = condition.toPresentation(),
        price = price,
        images = images,
        quantity = quantity,
        location = location.toPresentation(),
        userDescription = userDescription
    )
}

fun Category.toPresentation(): UiCategory = UiCategory.valueOf(this.name)
fun Condition.toPresentation(): UiCondition = UiCondition.valueOf(this.name)
fun Location.toPresentation(): UiLocation = UiLocation.valueOf(this.name)