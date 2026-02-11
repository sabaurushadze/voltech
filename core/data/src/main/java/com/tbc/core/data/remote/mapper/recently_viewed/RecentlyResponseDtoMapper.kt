package com.tbc.core.data.remote.mapper.recently_viewed

import com.tbc.core.data.remote.dto.recently_viewed.RecentlyResponseDto
import com.tbc.core.domain.model.recently_viewed.Recently


fun RecentlyResponseDto.toDomain() =
    Recently(
        id = id,
        uid = uid,
        itemId = itemId,
    )