package com.tbc.selling.presentation.screen.add_item

import android.net.Uri
import com.tbc.core.domain.model.category.Category
import com.tbc.core.presentation.model.UiUser
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.Location


data class AddItemState(
    val user: UiUser? = null,
    val isLoading: Boolean = false,


    val selectedCategory: Category? = null,
    val selectedCondition: Condition? = null,
    val selectedLocation: Location? = null,

    val selectedImageUris: List<Uri> = emptyList(),
    val previewStartIndex: Int? = null,

    val title: String = "",
    val description: String = "",
    val price: String = "",
    val quantity: String = "",

    val showTitleError: Boolean = false,
    val showDescriptionError: Boolean = false,
    val showPriceError: Boolean = false,
    val showCategoryError: Boolean = false,
    val showConditionError: Boolean = false,
    val showLocationError: Boolean = false,
    val showQuantityError: Boolean = false,
    val showImageError: Boolean = false,
)