package com.tbc.search.data.service.feed

import com.tbc.search.data.dto.feed.patch.ItemStatusPatchDto
import com.tbc.search.data.dto.feed.request.ItemRequestDto
import com.tbc.search.data.dto.feed.response.FeedItemResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface FeedService {
    @GET(ITEMS)
    suspend fun getItemsWithPagination(
        @Query(TITLE_LIKE) query: String? = null,
        @Query(CATEGORY, encoded = true) category: List<String>? = null,
        @Query(CONDITION, encoded = true) condition: List<String>? = null,
        @Query(LOCATION, encoded = true) location: List<String>? = null,
        @Query(PRICE_GREATER_THAN) minPrice: Float? = null,
        @Query(PRICE_LESS_THAN) maxPrice: Float? = null,
        @Query(ORDER) order: String? = null,
        @Query(SORT) sortBy: String? = null,
        @Query(PAGE) page: Int,
        @Query(PER_PAGE) perPage: Int,
        @Query(ACTIVE) active: Boolean,
    ): Response<List<FeedItemResponseDto>>

    @GET("$ITEMS/{id}")
    suspend fun getItemDetails(
        @Path("id") id: Int,
    ): Response<FeedItemResponseDto>

    @GET(ITEMS)
    suspend fun getItemsByIds(
        @Query("id") ids: List<Int>,
    ): Response<List<FeedItemResponseDto>>

    @GET(ITEMS)
    suspend fun getItemsByUid(
        @Query(UID) uid: String,
        @Query(ACTIVE) active: Boolean = true,
    ): Response<List<FeedItemResponseDto>>

    @POST(ITEMS)
    suspend fun addItem(
        @Body body: ItemRequestDto,
    ): Response<Unit>

    @DELETE("items/{id}")
    suspend fun deleteItem(
        @Path("id") id: Int,
    ): Response<Unit>

    @PATCH("items/{id}")
    suspend fun updateItem(
        @Path("id") id: Int,
        @Body itemStatusPatchDto: ItemStatusPatchDto,
    ): Response<Unit>


    companion object {
        private const val TITLE_LIKE = "title_like"
        private const val CATEGORY = "category"
        private const val CONDITION = "condition"
        private const val LOCATION = "location"
        private const val PRICE_GREATER_THAN = "price_gte"
        private const val PRICE_LESS_THAN = "price_lte"
        private const val SORT = "_sort"
        private const val ORDER = "_order"
        private const val ITEMS = "items"
        private const val PAGE = "_page"
        private const val PER_PAGE = "_per_page"
        private const val UID = "uid"
        private const val ACTIVE = "active"
    }
}