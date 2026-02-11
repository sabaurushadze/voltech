package com.tbc.search.data.repository.favorite

import com.tbc.core.data.remote.util.ApiResponseHandler
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core.domain.util.mapList
import com.tbc.search.data.dto.favorite.request.FavoriteRequestDto
import com.tbc.search.data.mapper.favorite.toDomain
import com.tbc.search.data.service.favorite.FavoriteService
import com.tbc.search.domain.model.favorite.Favorite
import com.tbc.search.domain.repository.favorite.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteService: FavoriteService,
    private val apiResponseHandler: ApiResponseHandler,
) : FavoriteRepository {
    override suspend fun getFavorites(uid: String): Resource<List<Favorite>, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            favoriteService.getFavoritesByUser(uid)
        }.mapList { it.toDomain() }
    }

    override suspend fun deleteFavoriteById(id: Int): Resource<Unit, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            favoriteService.deleteFavorite(id)
        }
    }

    override suspend fun toggleFavorite(
        uid: String,
        itemId: Int,
        favorites: List<Favorite>,
        favoriteAt: String
    ): Resource<Unit, DataError.Network> {
        val existing = favorites.firstOrNull {
            it.uid == uid && it.itemId == itemId
        }

        return if (existing == null) {
            apiResponseHandler.safeApiCall {
                favoriteService.addFavorite(
                    FavoriteRequestDto(
                        uid = uid,
                        itemId = itemId,
                        favoriteAt = favoriteAt
                    )
                )
            }
        } else {
            apiResponseHandler.safeApiCall {
                favoriteService.deleteFavorite(existing.id)
            }
        }
    }
}