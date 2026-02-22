package com.tbc.search.data.mapper.feed

import com.tbc.core.domain.model.category.Category
import com.tbc.core.domain.util.enumValueOfOrNull
import com.tbc.search.data.dto.feed.response.FeedItemResponseDto
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.model.feed.Location

internal fun FeedItemResponseDto.toDomain(): FeedItem {
    return FeedItem(
        id = id,
        uid = uid,
        title = title,
        category = enumValueOfOrNull<Category>(category) ?: Category.GPU,
        condition = enumValueOfOrNull<Condition>(condition) ?: Condition.NEW,
        price = price,
        images = images,
        quantity = quantity,
        location = enumValueOfOrNull<Location>(location) ?: Location.DIDI_DIGHOMI,
        userDescription = userDescription,
        active = active,
    )
}