package com.tbc.search.presentation.screen.item_details

import com.tbc.selling.domain.model.Rating


sealed class ItemDetailsEvent {
    data object GetCurrentSeller : ItemDetailsEvent()
    data class GetItemDetails(val id: Int) : ItemDetailsEvent()
    data class GetItemId(val id: Int) : ItemDetailsEvent()
    data class SelectImageByIndex(val index: Int) : ItemDetailsEvent()
    data class GetFavorites(val uid: String) : ItemDetailsEvent()
    data class OnFavoriteToggle(val uid: String) : ItemDetailsEvent()
    data object NavigateBackToFeed : ItemDetailsEvent()
    data object AddRecentlyItem : ItemDetailsEvent()
    data object AddItemToCart : ItemDetailsEvent()
    data object BuyItem : ItemDetailsEvent()
    data object GetCartItemIds : ItemDetailsEvent()
    data object GetFavoriteItems : ItemDetailsEvent()


    data object ShowReviewSheet : ItemDetailsEvent()
    data object HideReviewSheet : ItemDetailsEvent()
    data class SelectRating(val rating: Rating) : ItemDetailsEvent()
    data class DescriptionChanged(val description: String) : ItemDetailsEvent()
    data object ClearDescription : ItemDetailsEvent()
    data object ClearReviewErrors : ItemDetailsEvent()
    data object SubmitReview : ItemDetailsEvent()


    data class OpenImagePreview(val index: Int) : ItemDetailsEvent()
    data object CloseImagePreview : ItemDetailsEvent()
}