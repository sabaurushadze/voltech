package com.tbc.home.presentation.screen.mapper

import com.tbc.core.presentation.mapper.category.toStringRes
import com.tbc.home.domain.model.CategoryItem
import com.tbc.home.presentation.screen.model.UiCategoryItem

fun CategoryItem.toPresentation() =
    UiCategoryItem(
        id = id,
        category = category,
        categoryNameRes = category.toStringRes(),
        image = image
    )

fun List<CategoryItem>.toPresentation() = this.map { it.toPresentation() }