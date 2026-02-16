package com.tbc.search.data.service.favorite

import com.tbc.search.data.dto.favorite.request.FavoriteRequestDto
import com.tbc.search.data.dto.favorite.response.FavoriteResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface FavoriteService {
    @GET(FAVORITES)
    suspend fun getFavoritesByUser(
        @Query(UID) uid: String,
        @Query(SORT) sort: String = FAVORITE_AT,
        @Query(ORDER) order: String = DESC,
    ): Response<List<FavoriteResponseDto>>

    @POST(FAVORITES)
    suspend fun addFavorite(
        @Body request: FavoriteRequestDto
    ): Response<Unit>

    @DELETE("favorites/{id}")
    suspend fun deleteFavorite(
        @Path("id") id: Int
    ): Response<Unit>

    companion object {
        private const val FAVORITES = "favorites"
        private const val UID = "uid"
        private const val SORT = "_sort"
        private const val ORDER = "_order"
        private const val FAVORITE_AT = "favoriteAt"
        private const val DESC = "desc"
    }
}