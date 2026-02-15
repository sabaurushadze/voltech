package com.tbc.profile.presentation.screen.recently_viewed


sealed class RecentlyViewedEvent {
    data object NavigateBackToProfile : RecentlyViewedEvent()
    data object GetRecentlyViewedItems : RecentlyViewedEvent()
    data class NavigateToItemDetails(val itemId: Int) : RecentlyViewedEvent()
    data object DeleteRecentlyItemById : RecentlyViewedEvent()
    data object EditModeOn : RecentlyViewedEvent()
    data object EditModeOff : RecentlyViewedEvent()
    data class ToggleItemForDeletion(val recentlyId: Int) : RecentlyViewedEvent()
    data class ToggleSelectAll(val selectAll: Boolean) : RecentlyViewedEvent()
}