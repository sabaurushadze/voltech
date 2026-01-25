package com.tbc.search.domain.model.feed

data class FeedQuery(
    val titleLike: String = "",
    val category: String? = "",
    val location: String = "",
    val condition: String? = "",
    val minPrice: Float? = null,
    val maxPrice: Float? = null,
    val sortBy: String? = "",
    val sortDescending: Boolean = false,
)