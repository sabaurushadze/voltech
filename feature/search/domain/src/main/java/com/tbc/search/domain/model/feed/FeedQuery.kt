package com.tbc.search.domain.model.feed

data class FeedQuery(
    val titleLike: String? = null,
    val category: String? = null,
    val location: String? = null,
    val condition: String? = null,
    val minPrice: Float? = null,
    val maxPrice: Float? = null,
    val sortBy: String? = null,
    val sortDescending: Boolean = false,
)