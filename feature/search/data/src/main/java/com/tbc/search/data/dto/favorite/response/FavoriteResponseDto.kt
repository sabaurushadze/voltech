package com.tbc.search.data.dto.favorite.response

import kotlinx.serialization.Serializable

@Serializable
internal data class FavoriteResponseDto(
    val id: Int,
    val uid: String,
    val itemId: Int
)