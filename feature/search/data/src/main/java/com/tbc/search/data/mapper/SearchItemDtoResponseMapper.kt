package com.tbc.search.data.mapper

import com.tbc.search.data.dto.SearchItemResponseDto
import com.tbc.search.domain.model.SearchItem
import kotlinx.serialization.InternalSerializationApi

fun SearchItemResponseDto.toDomain(): SearchItem {
    return SearchItem(
        title = title
    )
}