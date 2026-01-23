package com.tbc.search.presentation.mapper

import com.tbc.search.domain.model.SearchItem
import com.tbc.search.presentation.model.UiSearchItem

fun SearchItem.toPresentation(): UiSearchItem {
    return UiSearchItem(
        title = title
    )
}