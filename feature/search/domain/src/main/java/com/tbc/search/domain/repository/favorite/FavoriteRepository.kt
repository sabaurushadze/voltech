package com.tbc.search.domain.repository.favorite

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.favorite.Favorite

interface FavoriteRepository {
    suspend fun getFavorites(uid: String) : Resource<List<Favorite>, DataError.Network>
    suspend fun toggleFavorite(
        uid: String,
        itemId: Int,
        favorites: List<Favorite>
    ) : Resource<Unit, DataError.Network>

    suspend fun deleteFavoriteById(id: Int) : Resource<Unit, DataError.Network>
}