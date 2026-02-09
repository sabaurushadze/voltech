package com.tbc.home.data.repository

import com.tbc.core.data.remote.util.ApiResponseHandler
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core.domain.util.mapList
import com.tbc.home.data.remote.mapper.toDomain
import com.tbc.home.data.remote.service.category.CategoryService
import com.tbc.home.domain.model.CategoryItem
import com.tbc.home.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val responseHandler: ApiResponseHandler
) : CategoryRepository{
    override suspend fun getCategories(): Resource<List<CategoryItem>, DataError.Network> {
        return responseHandler.safeApiCall {
            categoryService.getCategories()
        }.mapList { it.toDomain() }
    }

}