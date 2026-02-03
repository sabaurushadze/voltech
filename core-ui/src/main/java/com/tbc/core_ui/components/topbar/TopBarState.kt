package com.tbc.core_ui.components.topbar

import androidx.annotation.DrawableRes

data class TopBarState(
    val title: String = "",
    val navigationIcon: TopBarAction? = null,
    val actions: List<TopBarAction> = emptyList(),
)

data class TopBarAction(
    @param:DrawableRes val icon: Int,
    val onClick: () -> Unit,
)