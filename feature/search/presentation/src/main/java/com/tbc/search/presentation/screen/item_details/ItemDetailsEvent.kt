package com.tbc.search.presentation.screen.item_details

sealed class ItemDetailsEvent {
    data class GetItemDetails(val id: Int) : ItemDetailsEvent()
    data class SelectImageByIndex(val index: Int) : ItemDetailsEvent()
}