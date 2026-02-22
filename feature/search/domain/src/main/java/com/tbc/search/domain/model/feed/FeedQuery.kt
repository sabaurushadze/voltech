package com.tbc.search.domain.model.feed

data class FeedQuery(
    val uid: String? = null,
    val titleLike: String? = null,
    val category: List<String>? = null,
    val location: List<String>? = null,
    val condition: List<String>? = null,
    val minPrice: Float? = null,
    val maxPrice: Float? = null,
    val sortBy: String? = null,
    val sortDescending: Boolean = false,
    val active: Boolean = true,
)