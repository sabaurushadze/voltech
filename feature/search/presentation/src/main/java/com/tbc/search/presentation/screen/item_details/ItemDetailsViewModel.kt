package com.tbc.search.presentation.screen.item_details

import android.util.Log.d
import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.recently_viewed.AddRecentlyItemUseCase
import com.tbc.core.domain.usecase.recently_viewed.GetRecentlyUseCase
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.toStringResId
import com.tbc.search.domain.usecase.favorite.GetCurrentUserUidUseCase
import com.tbc.search.domain.usecase.favorite.GetFavoriteItemsUseCase
import com.tbc.search.domain.usecase.favorite.ToggleFavoriteItemUseCase
import com.tbc.search.domain.usecase.feed.GetItemDetailsUseCase
import com.tbc.search.presentation.mapper.favorite.toDomain
import com.tbc.search.presentation.mapper.favorite.toPresentation
import com.tbc.search.presentation.mapper.feed.toPresentation
import com.tbc.search.presentation.mapper.recently_viewed.toDomain
import com.tbc.search.presentation.model.recently_viewed.UiRecentlyRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItemDetailsViewModel @Inject constructor(
    private val getItemDetailsUseCase: GetItemDetailsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUidUseCase,
    private val getFavoriteItemsUseCase: GetFavoriteItemsUseCase,
    private val toggleFavoriteItemUseCase: ToggleFavoriteItemUseCase,
    private val addRecentlyItemUseCase: AddRecentlyItemUseCase,
    private val getRecentlyUseCase: GetRecentlyUseCase,
) : BaseViewModel<ItemDetailsState, ItemDetailsSideEffect, ItemDetailsEvent>(ItemDetailsState()) {

    override fun onEvent(event: ItemDetailsEvent) {
        when (event) {
            is ItemDetailsEvent.GetItemDetails -> getItemDetails(event.id)
            is ItemDetailsEvent.GetItemId -> updateState { copy(itemId = event.id) }
            is ItemDetailsEvent.SelectImageByIndex -> updateState { copy(selectedImage = event.index) }
            ItemDetailsEvent.NavigateBackToFeed -> navigateBackToFeed()
            ItemDetailsEvent.GetUserUid -> getUserUid()
            is ItemDetailsEvent.GetFavorites -> getFavorites(event.uid)
            is ItemDetailsEvent.OnFavoriteToggle ->
                toggleFavorite(uid = event.uid, itemId = event.itemId)
            ItemDetailsEvent.AddRecentlyItem -> addRecentlyItem()
        }
    }

    private fun toggleFavorite(uid: String, itemId: Int) {
        viewModelScope.launch {
            toggleFavoriteItemUseCase(
                uid = uid,
                itemId = itemId,
                favorites = state.value.favoriteItem.map { it.toDomain() }
            )
                .onSuccess {
                    getFavorites(uid)
                }
                .onFailure {
                    emitSideEffect(
                        ItemDetailsSideEffect.ShowSnackBar(it.toStringResId())
                    )
                }
        }
    }
    private fun getFavorites(uid: String) {
        viewModelScope.launch {
            getFavoriteItemsUseCase(uid)
                .onSuccess { favoriteDomain ->
                    updateState { copy(favoriteItem = favoriteDomain.map { it.toPresentation() }) }
                }
        }
    }

    private fun addRecentlyItem(){
        val recentlyItem = getRecentlyItem()
        viewModelScope.launch {
            getRecentlyUseCase(state.value.uid)
                .onSuccess { recentlyViewedDomain ->
                    updateState { copy(recentlyItemsId = recentlyViewedDomain.map { it.itemId }) }
                    if (!state.value.recentlyItemsId.contains(recentlyItem.itemId)){
                        addRecentlyItemUseCase(recentlyItem.toDomain())
                            .onSuccess { d("SUCCESS", "added") }
                            .onFailure { d("SUCCESS", "$it") }
                    }
                }
        }
    }

    private fun getUserUid() {
        val uid = getCurrentUserUseCase()
        uid?.let {
            updateState { copy(uid = it) }
            getFavorites(it)
        }
    }

    private fun navigateBackToFeed() {
        emitSideEffect(ItemDetailsSideEffect.NavigateBackToFeed)
    }

    private fun getItemDetails(id: Int) = viewModelScope.launch {
        updateState { copy(isLoading = true) }

        getItemDetailsUseCase(id)
            .onSuccess { itemDetailsDomain ->
                updateState { copy(itemDetails = itemDetailsDomain.toPresentation()) }
                updateState { copy(isLoading = false) }
            }
            .onFailure {
                emitSideEffect(ItemDetailsSideEffect.ShowSnackBar(errorRes = it.toStringResId()))
                updateState { copy(isLoading = false) }
            }
    }

    private fun getRecentlyItem(): UiRecentlyRequest {
        return UiRecentlyRequest(
            uid = state.value.uid,
            itemId = state.value.itemId
        )
    }

}