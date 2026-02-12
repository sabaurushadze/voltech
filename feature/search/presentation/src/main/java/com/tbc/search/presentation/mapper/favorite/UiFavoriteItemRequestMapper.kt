package com.tbc.search.presentation.mapper.favorite

import com.tbc.core.presentation.util.toIsoFormat
import com.tbc.search.domain.model.favorite.FavoriteRequestItem
import com.tbc.search.presentation.model.favorite.UiFavoriteItemRequest
import java.util.Date

fun UiFavoriteItemRequest.toDomain() =
    FavoriteRequestItem(
        uid = uid,
        itemId = itemId,
        favorites = favorites,
        favoriteAt = Date().toIsoFormat()
    )