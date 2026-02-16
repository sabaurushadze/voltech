package com.tbc.core.data.remote.dto.recently_viewed.request

import kotlinx.serialization.Serializable

@Serializable
internal data class RecentlyRequestDto (
    val uid: String,
    val itemId: Int,
    val viewedAt: String
)