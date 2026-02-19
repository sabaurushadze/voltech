package com.tbc.search.data.mapper.favorite

import com.tbc.search.data.dto.feed.patch.ItemStatusPatchDto
import com.tbc.search.domain.model.feed.ItemStatus

internal fun ItemStatus.toDto() =
    ItemStatusPatchDto(
        active = active
    )
