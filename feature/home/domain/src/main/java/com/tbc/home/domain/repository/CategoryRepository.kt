package com.tbc.home.domain.repository

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.home.domain.model.CategoryItem

interface CategoryRepository {
    suspend fun getCategories(): Resource<List<CategoryItem>, DataError.Network>
}