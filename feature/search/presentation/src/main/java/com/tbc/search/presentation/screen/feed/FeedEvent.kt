package com.tbc.search.presentation.screen.feed

import com.tbc.search.domain.model.feed.Category
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.Location
import com.tbc.search.presentation.enums.feed.SortType

sealed class FeedEvent {
    data class GetItems(val query: String) : FeedEvent()

    data class SaveSearchQuery(val query: String) : FeedEvent()
    data object ShowSortSheet : FeedEvent()
    data object HideSortSheet : FeedEvent()
    data class SelectSortType(val sortType: SortType) : FeedEvent()

    data class UpdateMinPrice(val value: String) : FeedEvent()
    data class UpdateMaxPrice(val value: String) : FeedEvent()

    data class ToggleCondition(val condition: Condition, val selected: Boolean) : FeedEvent()
    data class ToggleLocation(val location: Location, val selected: Boolean) : FeedEvent()
    data class ToggleCategory(val category: Category, val selected: Boolean) : FeedEvent()
    data object ShowFilterSheet : FeedEvent()
    data object HideFilterSheet : FeedEvent()
    data object FilterItems: FeedEvent()
}