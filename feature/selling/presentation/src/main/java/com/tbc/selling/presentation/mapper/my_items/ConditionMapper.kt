package com.tbc.selling.presentation.mapper.my_items

import com.tbc.resource.R
import com.tbc.search.domain.model.feed.Condition

fun Condition.toStringRes(): Int {
    return when (this) {
        Condition.NEW -> R.string.condition_new
        Condition.USED -> R.string.condition_used
        Condition.PARTS -> R.string.condition_parts
    }
}