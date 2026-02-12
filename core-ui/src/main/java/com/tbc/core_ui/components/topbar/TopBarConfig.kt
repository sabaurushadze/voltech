package com.tbc.core_ui.components.topbar

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
data class TopBarConfig(
    @param:StringRes val title: Int? = null,
    val showBackButton: Boolean = false,
    val backButtonAction: (() -> Unit)? = null,
    val searchContent: (@Composable () -> Unit)? = null,
    val actions: List<TopBarAction> = emptyList()
)
data class TopBarAction(
    val iconRes: Int,
    val onClick: () -> Unit
)