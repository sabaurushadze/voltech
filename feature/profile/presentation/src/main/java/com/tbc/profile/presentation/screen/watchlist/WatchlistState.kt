package com.tbc.profile.presentation.screen.watchlist

import com.tbc.core.presentation.model.UiUser
import com.tbc.profile.presentation.model.watchlist.UiFavorite

data class WatchlistState(
    val user: UiUser? = null,
    val isLoading: Boolean = true,
    val showNoConnectionError: Boolean = false,

    val favoriteItems: List<UiFavorite> = emptyList(),
    val editModeOn: Boolean = false,
) {
    val allSelected: Boolean
        get() = favoriteItems.isNotEmpty() && favoriteItems.all { it.isSelected }

    val anySelected: Boolean
        get() = favoriteItems.any { it.isSelected }

    val selectedCount: Int
        get() = favoriteItems.count { it.isSelected }
}

