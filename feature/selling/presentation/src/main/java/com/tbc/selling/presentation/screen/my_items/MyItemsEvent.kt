package com.tbc.selling.presentation.screen.my_items


sealed class MyItemsEvent {
    data class GetMyItems(val uid: String) : MyItemsEvent()
    data object NavigateToAddItem : MyItemsEvent()
}