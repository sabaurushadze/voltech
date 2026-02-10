package com.tbc.selling.presentation.mapper.my_items

import com.tbc.resource.R
import com.tbc.search.domain.model.feed.Location

fun Location.toStringRes(): Int {
    return when (this) {
        Location.DIDI_DIGHOMI -> R.string.didi_digohmi
        Location.GLDANI -> R.string.gldani
    }
}