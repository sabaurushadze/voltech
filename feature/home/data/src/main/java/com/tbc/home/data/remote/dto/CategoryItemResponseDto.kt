package com.tbc.home.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryItemResponseDto (
    val id: Int,
    val category: String,
    val image: String?
)