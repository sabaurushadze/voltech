package com.tbc.search.data.service

import com.tbc.search.data.dto.SearchItemResponseDto
import kotlinx.serialization.InternalSerializationApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchItemService {
    @GET(ITEMS)
    suspend fun searchItemsByQuery(
        @Query("title_like") query: String
    ): Response<List<SearchItemResponseDto>>
    companion object {
        private const val ITEMS = "items"
    }
}