package com.tbc.search.presentation.model.favorite

import com.tbc.search.domain.model.favorite.Favorite

data class UiFavoriteItemRequest (
    val uid: String,
    val itemId: Int,
    val favorites: List<Favorite>
)