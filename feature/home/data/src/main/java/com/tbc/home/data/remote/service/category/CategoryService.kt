package com.tbc.home.data.remote.service.category

import com.tbc.home.data.remote.dto.CategoryItemResponseDto
import retrofit2.Response
import retrofit2.http.GET

internal interface CategoryService {
    @GET(CATEGORIES)
    suspend fun getCategories(): Response<List<CategoryItemResponseDto>>

    companion object{
        private const val CATEGORIES = "categories"
    }
}