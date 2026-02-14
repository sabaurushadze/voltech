package com.tbc.profile.presentation.screen.recently_viewed

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.recently_viewed.DeleteRecentlyViewedByIdUseCase
import com.tbc.core.domain.usecase.recently_viewed.GetRecentlyUseCase
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.user.toPresentation
import com.tbc.profile.presentation.mapper.watchlist.toPresentation
import com.tbc.search.domain.usecase.feed.GetItemsByIdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecentlyViewedViewModel @Inject constructor(
    private val getRecentlyUseCase: GetRecentlyUseCase,
    private val getItemsByIdsUseCase: GetItemsByIdsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val deleteRecentlyViewedByIdUseCase: DeleteRecentlyViewedByIdUseCase
): BaseViewModel<RecentlyViewedState, RecentlyViewedSideEffect, RecentlyViewedEvent>(RecentlyViewedState()){

    init {
        getCurrentUser()
    }

    override fun onEvent(event: RecentlyViewedEvent) {
        when(event){
            RecentlyViewedEvent.DeleteRecentlyItemById -> deleteRecentlyViewedItemIds()
            RecentlyViewedEvent.EditModeOff -> turnOffEditMode()
            RecentlyViewedEvent.EditModeOn -> turnOnEditMode()
            RecentlyViewedEvent.GetRecentlyViewedItems -> getRecentlyViewedItems()
            RecentlyViewedEvent.NavigateBackToProfile -> navigateBackToProfile()
            is RecentlyViewedEvent.NavigateToItemDetails -> navigateToItemDetails(event.itemId)
            is RecentlyViewedEvent.ToggleItemForDeletion -> toggleItemSelection(event.favoriteId)
            is RecentlyViewedEvent.ToggleSelectAll -> toggleSelectAll(event.selectAll)
        }
    }

    private fun navigateToItemDetails(itemId: Int) {
        emitSideEffect(RecentlyViewedSideEffect.NavigateToItemDetails(itemId))
    }

    private fun navigateBackToProfile() {
        emitSideEffect(RecentlyViewedSideEffect.NavigateBackToProfile)
    }

    private fun turnOnEditMode() {
        updateState { copy(editModeOn = true) }
    }

    private fun turnOffEditMode() {
        updateState {
            copy(
                editModeOn = false,
                recentlyViewedItems = recentlyViewedItems.map { it.copy(isSelected = false) }
            )
        }
    }

    fun toggleItemSelection(favoriteId: Int) {
        updateState {
            copy(
                recentlyViewedItems = recentlyViewedItems.map { item ->
                    if (item.favoriteId == favoriteId) {
                        item.copy(isSelected = !item.isSelected)
                    } else item
                }
            )
        }
    }

    private fun toggleSelectAll(selectAll: Boolean) {
        updateState {
            copy(
                recentlyViewedItems = recentlyViewedItems.map { it.copy(isSelected = selectAll) }
            )
        }
    }

    private fun getRecentlyViewedItems() = viewModelScope.launch {
        val user = state.value.user ?: return@launch

        getRecentlyUseCase(user.uid)
            .onSuccess { recently ->

                if (recently.isEmpty()) {
                    updateState { copy(recentlyViewedItems = emptyList(), isLoading = false) }
                    return@onSuccess
                }

                val itemIds = recently.map { it.itemId }

                getItemsByIdsUseCase(itemIds)
                    .onSuccess { items ->

                        val uiRecently = itemIds.mapNotNull { id ->
                            val item = items.firstOrNull { it.id == id } ?: return@mapNotNull null
                            val recently = recently.first { it.itemId == id }

                            item.toPresentation(
                                favoriteId = recently.id
                            )
                        }

                        updateState { copy(recentlyViewedItems = uiRecently, isLoading = false) }

                    }
                    .onFailure {
                        updateState { copy(isLoading = false) }
                    }
            }
            .onFailure {
                updateState { copy(isLoading = false) }
            }
    }

    private fun getCurrentUser() = viewModelScope.launch {
        val currentUser = getCurrentUserUseCase()?.toPresentation()
        updateState { copy(user = currentUser) }
    }

    private fun deleteRecentlyViewedItemIds() = viewModelScope.launch {
        val selectedFavoriteIds = state.value.recentlyViewedItems
            .filter { it.isSelected }
            .map { it.favoriteId }

        selectedFavoriteIds.forEach { favoriteId ->
            deleteRecentlyViewedByIdUseCase(favoriteId)
        }

        getRecentlyViewedItems()
    }

}