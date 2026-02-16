package com.tbc.profile.presentation.screen.watchlist

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.user.toPresentation
import com.tbc.profile.presentation.mapper.watchlist.toPresentation
import com.tbc.search.domain.usecase.favorite.DeleteFavoriteItemByIdUseCase
import com.tbc.search.domain.usecase.favorite.GetFavoriteItemsUseCase
import com.tbc.search.domain.usecase.feed.GetItemsByIdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val getFavoriteItemsUseCase: GetFavoriteItemsUseCase,
    private val deleteFavoriteItemByIdUseCase: DeleteFavoriteItemByIdUseCase,
    private val getItemsByIdsUseCase: GetItemsByIdsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
) : BaseViewModel<WatchlistState, WatchlistSideEffect, WatchlistEvent>(WatchlistState()) {

    init {
        getCurrentUser()
    }

    override fun onEvent(event: WatchlistEvent) {
        when (event) {
            WatchlistEvent.NavigateBackToProfile -> navigateBackToProfile()
            WatchlistEvent.GetFavoriteItems -> getFavoriteItems()
            is WatchlistEvent.NavigateToItemDetails -> navigateToItemDetails(event.itemId)
            is WatchlistEvent.DeleteFavoriteItemById -> deleteFavoriteItemIds()
            WatchlistEvent.EditModeOn -> turnOnEditMode()
            WatchlistEvent.EditModeOff -> turnOffEditMode()
            is WatchlistEvent.ToggleItemForDeletion -> toggleItemSelection(event.favoriteId)
            is WatchlistEvent.ToggleSelectAll -> toggleSelectAll(event.selectAll)
        }
    }

    private fun turnOnEditMode() {
        updateState { copy(editModeOn = true) }
    }

    private fun turnOffEditMode() {
        updateState {
            copy(
                editModeOn = false,
                favoriteItems = favoriteItems.map { it.copy(isSelected = false) }
            )
        }
    }

    private fun navigateToItemDetails(itemId: Int) {
        emitSideEffect(WatchlistSideEffect.NavigateToItemDetails(itemId))
    }

    private fun navigateBackToProfile() {
        emitSideEffect(WatchlistSideEffect.NavigateBackToProfile)
    }

    private fun toggleSelectAll(selectAll: Boolean) {
        updateState {
            copy(
                favoriteItems = favoriteItems.map { it.copy(isSelected = selectAll) }
            )
        }
    }

    fun toggleItemSelection(favoriteId: Int) {
        updateState {
            copy(
                favoriteItems = favoriteItems.map { item ->
                    if (item.favoriteId == favoriteId) {
                        item.copy(isSelected = !item.isSelected)
                    } else item
                }
            )
        }
    }

    private fun getFavoriteItems() = viewModelScope.launch {
        updateState { copy(isLoading = true) }
        val user = state.value.user ?: return@launch

        getFavoriteItemsUseCase(user.uid)
            .onSuccess { favorites ->

                if (favorites.isEmpty()) {
                    updateState {
                        copy(
                            favoriteItems = emptyList(),
                            isLoading = false,
                            showNoConnectionError = false
                        )
                    }
                    return@onSuccess
                }

                val itemIds = favorites.map { it.itemId }

                getItemsByIdsUseCase(itemIds)
                    .onSuccess { items ->

                        val uiFavorites = items.map { item ->
                            val favorite = favorites.first { it.itemId == item.id }

                            item.toPresentation(
                                favoriteId = favorite.id
                            )
                        }

                        updateState {
                            copy(
                                favoriteItems = uiFavorites,
                                isLoading = false,
                                showNoConnectionError = false
                            )
                        }

                    }
                    .onFailure { result ->
                        if (result == DataError.Network.NO_CONNECTION) {
                            updateState {
                                copy(
                                    isLoading = false,
                                    showNoConnectionError = true
                                )
                            }

                        } else {
                            updateState { copy(isLoading = false, showNoConnectionError = false) }
                        }
                    }
            }
            .onFailure { result ->
                if (result == DataError.Network.NO_CONNECTION) {
                    updateState {
                        copy(
                            isLoading = false,
                            showNoConnectionError = true
                        )
                    }

                } else {
                    updateState { copy(isLoading = false, showNoConnectionError = false) }
                }
            }
    }

    private fun getCurrentUser() = viewModelScope.launch {
        val currentUser = getCurrentUserUseCase()?.toPresentation()
        updateState { copy(user = currentUser) }
    }

    private fun deleteFavoriteItemIds() = viewModelScope.launch {
        val selectedFavoriteIds = state.value.favoriteItems
            .filter { it.isSelected }
            .map { it.favoriteId }

        selectedFavoriteIds.forEach { favoriteId ->
            deleteFavoriteItemByIdUseCase(favoriteId)
        }

        getFavoriteItems()
    }
}