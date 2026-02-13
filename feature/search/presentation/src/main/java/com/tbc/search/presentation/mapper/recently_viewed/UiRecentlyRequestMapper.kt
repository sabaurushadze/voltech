package com.tbc.search.presentation.mapper.recently_viewed

import com.tbc.core.domain.model.recently_viewed.RecentlyRequest
import com.tbc.core.presentation.util.toIsoFormat
import com.tbc.search.presentation.model.recently_viewed.UiRecentlyRequest
import java.util.Date

fun UiRecentlyRequest.toDomain() =
    RecentlyRequest(
        uid = uid,
        itemId = itemId,
        viewedAt = Date().toIsoFormat(),
    )