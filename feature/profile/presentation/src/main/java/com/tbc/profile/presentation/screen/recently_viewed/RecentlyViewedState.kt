package com.tbc.profile.presentation.screen.recently_viewed

import com.tbc.core.presentation.model.UiUser
import com.tbc.profile.presentation.model.watchlist.UiFavorite

data class RecentlyViewedState (
    val user: UiUser? = null,
    val isLoading: Boolean = true,


    val recentlyViewedItems: List<UiFavorite> = emptyList(),
    val editModeOn: Boolean = false,
)