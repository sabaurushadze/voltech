package com.tbc.search.presentation.mapper.favorite

import com.tbc.search.domain.model.favorite.FavoriteRequestItem
import com.tbc.search.presentation.model.favorite.UiFavoriteItemRequest
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun UiFavoriteItemRequest.toDomain() =
    FavoriteRequestItem(
        uid = uid,
        itemId = itemId,
        favorites = favorites,
        favoriteAt = Clock.System.now().toString()
    )