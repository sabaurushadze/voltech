package com.tbc.core.data.remote.mapper.recently_viewed

import com.tbc.core.data.remote.dto.recently_viewed.request.RecentlyRequestDto
import com.tbc.core.domain.model.recently_viewed.RecentlyRequest

internal fun RecentlyRequest.toData() =
    RecentlyRequestDto(
        uid = uid,
        itemId = itemId,
        viewedAt = viewedAt,
    )