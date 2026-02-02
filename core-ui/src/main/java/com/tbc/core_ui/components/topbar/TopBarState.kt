package com.tbc.core_ui.components.topbar

import androidx.compose.ui.graphics.vector.ImageVector

data class TopBarState(
    val title: String = "",
    val navigationIcon: TopBarAction? = null,
    val actions: List<TopBarAction> = emptyList(),
)

data class TopBarAction(
    val icon: ImageVector,
    val onClick: () -> Unit,
)