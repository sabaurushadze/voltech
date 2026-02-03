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

interface FavoriteService {
    @GET(FAVORITES)
    suspend fun getFavoritesByUser(
        @Query("uid") uid: String,
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
    }
}