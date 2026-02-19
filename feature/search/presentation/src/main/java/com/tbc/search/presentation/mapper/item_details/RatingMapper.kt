package com.tbc.search.presentation.mapper.item_details

import com.tbc.resource.R
import com.tbc.selling.domain.model.Rating

fun Rating.toStringRes(): Int {
    return when (this) {
        Rating.POSITIVE -> R.string.positive
        Rating.NEUTRAL -> R.string.neutral
        Rating.NEGATIVE -> R.string.negative
    }
}

fun Rating.toIconRes(): Int {
    return when (this) {
        Rating.POSITIVE -> R.drawable.ic_positive
        Rating.NEUTRAL -> R.drawable.ic_neutral
        Rating.NEGATIVE -> R.drawable.ic_negative
    }
}
