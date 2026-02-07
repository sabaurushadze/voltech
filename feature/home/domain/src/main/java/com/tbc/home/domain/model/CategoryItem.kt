package com.tbc.home.domain.model

import com.tbc.core.domain.model.category.Category

data class CategoryItem (
    val id: Int,
    val category: Category,
    val image: String?
)