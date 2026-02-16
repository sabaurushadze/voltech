package com.tbc.search.data.dto.favorite.request

import kotlinx.serialization.Serializable

@Serializable
internal data class FavoriteRequestDto(
    val uid: String,
    val itemId: Int,
    val favoriteAt: String
)