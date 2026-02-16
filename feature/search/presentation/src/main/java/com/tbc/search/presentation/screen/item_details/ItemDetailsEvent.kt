package com.tbc.search.presentation.screen.item_details


sealed class ItemDetailsEvent {
    data class GetItemDetails(val id: Int) : ItemDetailsEvent()
    data class GetItemId(val id: Int) : ItemDetailsEvent()
    data class SelectImageByIndex(val index: Int) : ItemDetailsEvent()
    data class GetFavorites(val uid: String) : ItemDetailsEvent()
    data class OnFavoriteToggle(val uid: String) : ItemDetailsEvent()
    data object NavigateBackToFeed : ItemDetailsEvent()
    data object AddRecentlyItem : ItemDetailsEvent()
    data object AddItemToCart : ItemDetailsEvent()
    data object BuyItem: ItemDetailsEvent()
    data object GetCartItemIds: ItemDetailsEvent()
    data object GetFavoriteItems: ItemDetailsEvent()
}