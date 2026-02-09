package com.tbc.core.data.remote.service.recently_viewed

import com.tbc.core.data.remote.dto.recently_viewed.RecentlyRequestDto
import com.tbc.core.data.remote.dto.recently_viewed.RecentlyResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RecentlyService {
    @GET(RECENTLY)
    suspend fun getRecentlyViewed(
        @Query("uid") uid: String,
    ): Response<List<RecentlyResponseDto>>

    @POST(RECENTLY)
    suspend fun addRecently(
        @Body body: RecentlyRequestDto
    ): Response<Unit>

    @DELETE("recently/{id}")
    suspend fun deleteRecentlyViewed(
        @Path("id") id: Int
    ): Response<Unit>

    companion object {
        private const val RECENTLY = "recently"
    }
}