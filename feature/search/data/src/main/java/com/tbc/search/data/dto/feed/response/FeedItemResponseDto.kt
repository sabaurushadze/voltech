package com.tbc.search.data.dto.feed.response

import kotlinx.serialization.Serializable

@Serializable
internal data class FeedItemResponseDto(
    val id: Int,
    val uid: String,
    val title: String,
    val category: String,
    val condition: String,
    val price: Double,
    val images: List<String>,
    val quantity: Int,
    val location: String,
    val userDescription: String,
    val active: Boolean,
)