package com.tbc.selling.presentation.model.add_item

import com.tbc.core.domain.model.category.Category
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.Location

data class UiItem(
    val uid: String,
    val title: String,
    val category: Category,
    val condition: Condition,
    val price: Double,
    val images: List<String>,
    val quantity: Int,
    val location: Location,
    val userDescription: String,
    val sellerAvatar: String?,
    val sellerUserName: String?,
)
