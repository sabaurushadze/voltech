package com.tbc.selling.presentation.mapper.my_items

import com.tbc.search.domain.model.feed.ItemStatus
import com.tbc.selling.presentation.model.my_items.UiItemStatus

fun UiItemStatus.toDomain() = ItemStatus(
    active = active
)
