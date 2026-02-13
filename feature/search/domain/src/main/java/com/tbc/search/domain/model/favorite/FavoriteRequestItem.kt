package com.tbc.search.domain.model.favorite

data class FavoriteRequestItem (
    val uid: String,
    val itemId: Int,
    val favorites: List<Favorite>,
    val favoriteAt: String
)