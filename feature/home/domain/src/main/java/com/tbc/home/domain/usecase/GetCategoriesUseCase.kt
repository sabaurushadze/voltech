package com.tbc.home.domain.usecase

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.home.domain.model.CategoryItem
import com.tbc.home.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
){
    suspend operator fun invoke(): Resource<List<CategoryItem>, DataError.Network>{
        return repository.getCategories()
    }
}