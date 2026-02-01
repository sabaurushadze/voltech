package com.tbc.search.data.dto.feed

import kotlinx.serialization.Serializable

@Serializable
data class FeedItemResponseDto(
    val id: Int,
    val title: String,
    val category: String,
    val condition: String,
    val price: Double,
    val images: List<String>,
    val quantity: Int,
    val location: String,
    val userDescription: String,
    val sellerAvatar: String?,
    val sellerUserName: String,
)