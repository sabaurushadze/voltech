package com.tbc.search.presentation.screen.item_details

sealed class ItemDetailsEvent {
    data class GetItemDetails(val id: Int) : ItemDetailsEvent()
    data class SelectImageByIndex(val index: Int) : ItemDetailsEvent()
    data object NavigateBackToFeed : ItemDetailsEvent()
    data object GetUserUid : ItemDetailsEvent()
    data class GetFavorites(val uid: String) : ItemDetailsEvent()
    data class OnFavoriteToggle(val uid: String, val itemId: Int) : ItemDetailsEvent()
}