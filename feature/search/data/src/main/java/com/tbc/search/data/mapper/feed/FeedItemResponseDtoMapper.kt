package com.tbc.search.data.mapper.feed

import com.tbc.core.domain.model.category.Category
import com.tbc.search.data.dto.feed.response.FeedItemResponseDto
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.model.feed.Location

internal fun FeedItemResponseDto.toDomain(): FeedItem {
    return FeedItem(
        id = id,
        uid = uid,
        title = title,
        category = Category.fromString(category),
        condition = Condition.fromString(condition),
        price = price,
        images = images,
        quantity = quantity,
        location = Location.fromString(location),
        userDescription = userDescription,
        sellerName = sellerName,
        sellerPhotoUrl = sellerPhotoUrl,
    )
}