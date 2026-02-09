package com.tbc.core.data.remote.dto.recently_viewed

import kotlinx.serialization.Serializable

@Serializable
data class RecentlyResponseDto (
    val id: Int,
    val uid: String,
    val itemId: Int,
)