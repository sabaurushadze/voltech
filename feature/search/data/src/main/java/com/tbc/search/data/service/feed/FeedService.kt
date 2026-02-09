package com.tbc.search.data.service.feed

import com.tbc.search.data.dto.feed.FeedItemResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FeedService {
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
    ): Response<List<FeedItemResponseDto>>

    @GET("$ITEMS/{id}")
    suspend fun getItemDetails(
        @Path("id") id: Int,
    ): Response<FeedItemResponseDto>

    @GET(ITEMS)
    suspend fun getItemsByIds(
        @Query("id") ids: List<Int>
    ): Response<List<FeedItemResponseDto>>

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
    }
}