package com.tbc.search.data.dto.feed.patch

import kotlinx.serialization.Serializable

@Serializable
internal data class ItemStatusPatchDto(
    val active: Boolean
)