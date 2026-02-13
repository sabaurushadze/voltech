package com.tbc.search.data.mapper.favorite

import com.tbc.search.data.dto.favorite.request.FavoriteRequestDto
import com.tbc.search.domain.model.favorite.FavoriteRequestItem

fun FavoriteRequestItem.toData() =
    FavoriteRequestDto(
        uid = uid,
        itemId = itemId,
        favoriteAt = favoriteAt
    )