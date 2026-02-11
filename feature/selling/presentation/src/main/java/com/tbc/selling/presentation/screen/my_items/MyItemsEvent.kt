package com.tbc.selling.presentation.screen.my_items


sealed class MyItemsEvent {
    data class GetMyItems(val uid: String) : MyItemsEvent()
    data class NavigateToItemDetails(val id: Int) : MyItemsEvent()
    data object NavigateToAddItem : MyItemsEvent()
    data object CanUserPostItems : MyItemsEvent()

//    delete section
    data class ToggleSelectAll(val selectAll: Boolean) : MyItemsEvent()
    data object EditModeOn : MyItemsEvent()
    data object EditModeOff : MyItemsEvent()
    data object DeleteFavoriteItemById : MyItemsEvent()
    data class ToggleItemForDeletion(val id: Int) : MyItemsEvent()



}