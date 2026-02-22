package com.tbc.selling.presentation.mapper.my_items

import com.tbc.core.domain.util.toServerString
import com.tbc.search.domain.model.feed.Item
import com.tbc.selling.presentation.model.add_item.UiItem

fun UiItem.toDomain() =
    Item(
        uid = uid,
        title = title,
        category = category.toServerString(),
        condition = condition.toServerString(),
        price = price.toDouble(),
        images = images,
        quantity = quantity.toInt(),
        location = location.toServerString(),
        userDescription = userDescription,
    )