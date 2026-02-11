package com.tbc.selling.presentation.screen.my_items

import com.tbc.core.presentation.model.UiUser
import com.tbc.selling.presentation.model.my_items.UiMyItem


data class MyItemsState(

    val user: UiUser? = null,

    val myItems: List<UiMyItem> = emptyList(),
    val editModeOn: Boolean = false,
    val isLoading: Boolean = true,

    val userCanAddItem: Boolean = false,
) {
    val allSelected: Boolean
        get() = myItems.isNotEmpty() && myItems.all { it.isSelected }

    val anySelected: Boolean
        get() = myItems.any { it.isSelected }

    val selectedCount: Int
        get() = myItems.count { it.isSelected }
}