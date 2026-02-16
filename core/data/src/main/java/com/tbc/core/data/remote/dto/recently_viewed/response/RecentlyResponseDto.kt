package com.tbc.core.data.remote.dto.recently_viewed.response

import kotlinx.serialization.Serializable

@Serializable
internal data class RecentlyResponseDto (
    val id: Int,
    val uid: String,
    val itemId: Int,
)