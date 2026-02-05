package com.tbc.core_ui.components.topbar

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable

data class TopBarState(
    val title: String? = null,
    val titleContent: (@Composable () -> Unit)? = null,
    val navigationIcon: TopBarAction? = null,
    val actions: List<TopBarAction> = emptyList(),
)

data class TopBarAction(
    @param:DrawableRes val icon: Int,
    val onClick: () -> Unit,
)