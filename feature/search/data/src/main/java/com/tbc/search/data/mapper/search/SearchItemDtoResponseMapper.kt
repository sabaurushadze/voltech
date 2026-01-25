package com.tbc.search.data.mapper.search

import com.tbc.search.data.dto.search.SearchItemResponseDto
import com.tbc.search.domain.model.search.SearchItem

fun SearchItemResponseDto.toDomain(): SearchItem {
    return SearchItem(
        title = title
    )
}