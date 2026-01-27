package com.tbc.search.presentation.mapper.feed

import com.tbc.search.presentation.R
import com.tbc.search.presentation.enums.feed.SortType

fun SortType.toStringRes(): Int {
    return when (this) {
        SortType.PRICE_HIGHEST -> R.string.sort_price_highest
        SortType.PRICE_LOWEST -> R.string.sort_price_lowest
    }
}