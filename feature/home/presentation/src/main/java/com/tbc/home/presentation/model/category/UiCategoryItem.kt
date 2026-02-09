package com.tbc.home.presentation.model.category

import androidx.annotation.StringRes
import com.tbc.core.domain.model.category.Category

data class UiCategoryItem (
    val id: Int,
    val category: Category,
    @param:StringRes
    val categoryNameRes: Int,
    val image: String?
)