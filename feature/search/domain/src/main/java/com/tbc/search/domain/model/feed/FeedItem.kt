package com.tbc.search.domain.model.feed

import com.tbc.core.domain.model.category.Category

data class FeedItem(
    val id: Int,
    val uid: String,
    val title: String,
    val category: Category,
    val condition: Condition,
    val price: Double,
    val images: List<String>,
    val quantity: Int,
    val location: Location,
    val userDescription: String,
    val active: Boolean,
)


enum class Condition {
    NEW,
    USED,
    PARTS;
}

enum class Location {
    DIDI_DIGHOMI,
    GLDANI;
}
