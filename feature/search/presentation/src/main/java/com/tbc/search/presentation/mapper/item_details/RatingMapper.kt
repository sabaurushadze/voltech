package com.tbc.search.presentation.mapper.item_details

import com.tbc.resource.R
import com.tbc.search.presentation.enums.item_details.Rating

fun Rating.toStringRes(): Int {
    return when (this) {
        Rating.POSITIVE -> R.string.positive
        Rating.NEUTRAL -> R.string.neutral
        Rating.NEGATIVE -> R.string.negative
    }
}