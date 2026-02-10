package com.tbc.selling.presentation.screen.my_items

import com.tbc.core.presentation.model.UiUser
import com.tbc.selling.presentation.model.my_items.UiMyItem


data class MyItemsState(

    val user: UiUser? = null,

    val myItems: List<UiMyItem> = emptyList(),
    val isLoading: Boolean = false,


)