package com.tbc.search.data.mapper.feed

import com.tbc.search.data.dto.feed.request.ItemRequestDto
import com.tbc.search.domain.model.feed.Item

fun Item.toDto() =
    ItemRequestDto(
        uid = uid,
        title = title,
        category = category,
        condition = condition,
        price = price,
        images = images,
        quantity = quantity,
        location = location,
        userDescription = userDescription,
        sellerAvatar = sellerAvatar,
        sellerUserName = sellerUserName
    )