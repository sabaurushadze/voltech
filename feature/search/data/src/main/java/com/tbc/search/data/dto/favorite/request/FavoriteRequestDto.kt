package com.tbc.search.data.dto.favorite.request

import kotlinx.serialization.Serializable

@Serializable
data class FavoriteRequestDto(
    val uid: String,
    val itemId: Int,
)