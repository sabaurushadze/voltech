package com.tbc.profile.presentation.screen.watchlist

import com.tbc.core.presentation.model.UiUser
import com.tbc.profile.presentation.model.watchlist.UiFavorite

data class WatchlistState(
    val user: UiUser? = null,
    val isLoading: Boolean = true,


    val favoriteItems: List<UiFavorite> = emptyList(),
    val editModeOn: Boolean = false,
)

