package com.tbc.selling.presentation.mapper.my_items

import com.tbc.core.domain.model.category.Category
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.Item
import com.tbc.search.domain.model.feed.Location
import com.tbc.selling.presentation.model.add_item.UiItem

fun UiItem.toDomain() =
    Item(
        uid = uid,
        title = title,
        category = Category.toServerString(category),
        condition = Condition.toServerString(condition),
        price = price.toDouble(),
        images = images,
        quantity = quantity.toInt(),
        location = Location.toServerString(location),
        userDescription = userDescription,
        sellerAvatar = sellerAvatar,
        sellerUserName = sellerUserName
    )