package com.tbc.search.presentation.mapper.search

import com.tbc.search.domain.model.search.SearchItem
import com.tbc.search.presentation.model.search.UiSearchItem

fun SearchItem.toPresentation(): UiSearchItem {
    return UiSearchItem(
        title = title
    )
}