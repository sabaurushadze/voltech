package com.tbc.home.data.remote.mapper

import com.tbc.core.domain.model.category.Category
import com.tbc.core.domain.util.enumValueOfOrNull
import com.tbc.home.data.remote.dto.CategoryItemResponseDto
import com.tbc.home.domain.model.CategoryItem


internal fun CategoryItemResponseDto.toDomain() =
    CategoryItem(
        id = id,
        category = enumValueOfOrNull<Category>(category) ?: Category.GPU,
        image = image
    )