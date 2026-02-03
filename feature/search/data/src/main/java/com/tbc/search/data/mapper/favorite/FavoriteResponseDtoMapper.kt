package com.tbc.search.data.mapper.favorite

import com.tbc.search.data.dto.favorite.response.FavoriteResponseDto
import com.tbc.search.domain.model.favorite.Favorite

fun FavoriteResponseDto.toDomain(): Favorite {
    return Favorite(
        id = id,
        uid = uid,
        itemId = itemId,
    )
}