package com.tbc.home.presentation.mapper.recently_viewed

import com.tbc.core.domain.model.recently_viewed.Recently
import com.tbc.home.presentation.model.recently_viewed.UiRecently

fun Recently.toPresentation() =
    UiRecently(
        itemId = itemId
    )

fun List<Recently>.toPresentation() = this.map { it.toPresentation() }