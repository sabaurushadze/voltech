package com.tbc.search.presentation.screen.feed

import com.tbc.search.domain.model.feed.Category
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.FeedQuery
import com.tbc.search.domain.model.feed.Location
import com.tbc.search.presentation.enums.feed.SortType

data class FeedState(
    val query: FeedQuery = FeedQuery(),
    val isLoading: Boolean = false,
    val selectedSort: Boolean = false,
    val selectedFilter: Boolean = false,
    val selectedSortType: SortType = SortType.PRICE_LOWEST,
    val filterState: FilterSheetState = FilterSheetState(),
)

data class FilterSheetState(
    val minPrice: String = "",
    val maxPrice: String = "",
    val selectedCategories: Set<Category> = setOf(),
    val selectedConditions: Set<Condition> = setOf(),
    val selectedLocations: Set<Location> = setOf(),
)