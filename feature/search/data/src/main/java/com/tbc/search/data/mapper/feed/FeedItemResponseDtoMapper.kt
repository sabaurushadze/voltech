package com.tbc.search.data.mapper.feed

import com.tbc.search.data.dto.feed.CategoryDto
import com.tbc.search.data.dto.feed.ConditionDto
import com.tbc.search.data.dto.feed.FeedItemResponseDto
import com.tbc.search.data.dto.feed.LocationDto
import com.tbc.search.domain.model.feed.Category
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.model.feed.Location

fun FeedItemResponseDto.toDomain(): FeedItem {
    return FeedItem(
        id = id,
        title = title,
        category = category.toDomain(),
        condition = condition.toDomain(),
        price = price,
        images = images,
        quantity = quantity,
        location = location.toDomain(),
        userDescription = userDescription
    )
}

fun CategoryDto.toDomain(): Category = Category.valueOf(this.name)
fun ConditionDto.toDomain(): Condition = Condition.valueOf(this.name)
fun LocationDto.toDomain(): Location = Location.valueOf(this.name)