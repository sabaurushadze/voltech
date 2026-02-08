package com.tbc.profile.presentation.screen.watchlist

sealed class WatchlistEvent {
    data object NavigateBackToProfile : WatchlistEvent()
    data object GetFavoriteItems : WatchlistEvent()
    data class NavigateToItemDetails(val itemId: Int) : WatchlistEvent()
    data object DeleteFavoriteItemById : WatchlistEvent()
    data object EditModeOn : WatchlistEvent()
    data object EditModeOff : WatchlistEvent()
    data class ToggleItemForDeletion(val favoriteId: Int) : WatchlistEvent()
    data class ToggleSelectAll(val selectAll: Boolean) : WatchlistEvent()
}