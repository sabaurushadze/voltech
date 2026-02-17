package com.tbc.search.presentation.screen.item_details

import android.util.Log.d
import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.recently_viewed.AddRecentlyItemUseCase
import com.tbc.core.domain.usecase.recently_viewed.GetRecentlyUseCase
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.toStringResId
import com.tbc.core.presentation.mapper.user.toPresentation
import com.tbc.resource.R
import com.tbc.search.domain.usecase.cart.AddItemToCartUseCase
import com.tbc.search.domain.usecase.cart.GetCartItemsUseCase
import com.tbc.search.domain.usecase.favorite.GetFavoriteItemsUseCase
import com.tbc.search.domain.usecase.favorite.ToggleFavoriteItemUseCase
import com.tbc.search.domain.usecase.feed.GetItemDetailsUseCase
import com.tbc.search.presentation.mapper.cart.toDomain
import com.tbc.search.presentation.mapper.favorite.toDomain
import com.tbc.search.presentation.mapper.favorite.toPresentation
import com.tbc.search.presentation.mapper.feed.toPresentation
import com.tbc.search.presentation.mapper.item_details.toPresentation
import com.tbc.search.presentation.mapper.recently_viewed.toDomain
import com.tbc.search.presentation.model.cart.UiCartItemRequest
import com.tbc.search.presentation.model.favorite.UiFavoriteItemRequest
import com.tbc.search.presentation.model.recently_viewed.UiRecentlyRequest
import com.tbc.search.presentation.screen.item_details.ItemDetailsSideEffect.NavigateBackToFeed
import com.tbc.search.presentation.screen.item_details.ItemDetailsSideEffect.ShowSnackBar
import com.tbc.selling.domain.usecase.selling.add_item.add_seller.GetSellersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ItemDetailsViewModel @Inject constructor(
    private val getItemDetailsUseCase: GetItemDetailsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getFavoriteItemsUseCase: GetFavoriteItemsUseCase,
    private val getRecentlyUseCase: GetRecentlyUseCase,
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val toggleFavoriteItemUseCase: ToggleFavoriteItemUseCase,
    private val addRecentlyItemUseCase: AddRecentlyItemUseCase,
    private val addItemToCartUseCase: AddItemToCartUseCase,
    private val getSellersUseCase: GetSellersUseCase
) :
    BaseViewModel<ItemDetailsState, ItemDetailsSideEffect, ItemDetailsEvent>(ItemDetailsState()) {

    init {
        getCurrentUser()
    }

    override fun onEvent(event: ItemDetailsEvent) {
        when (event) {
            is ItemDetailsEvent.GetItemDetails -> getItemDetails(event.id)
            is ItemDetailsEvent.GetItemId -> updateState { copy(itemId = event.id) }
            is ItemDetailsEvent.SelectImageByIndex -> updateState { copy(selectedImage = event.index) }
            is ItemDetailsEvent.GetFavorites -> getFavorites(event.uid)
            is ItemDetailsEvent.OnFavoriteToggle -> toggleFavorite(uid = event.uid)
            ItemDetailsEvent.NavigateBackToFeed -> navigateBackToFeed()
            ItemDetailsEvent.AddRecentlyItem -> addRecentlyItem()
            ItemDetailsEvent.AddItemToCart -> addItemToCart()
            ItemDetailsEvent.BuyItem -> emitSideEffect(ShowSnackBar(R.string.this_service_not_work))
            ItemDetailsEvent.GetCartItemIds -> getCartItemIds()
            ItemDetailsEvent.GetFavoriteItems -> getFavorites(state.value.user.uid)


            ItemDetailsEvent.CloseImagePreview -> updateState { copy(previewStartIndex = null) }
            is ItemDetailsEvent.OpenImagePreview -> updateState { copy(previewStartIndex = event.index) }
        }
    }

    private fun toggleFavorite(uid: String) {
        val favoriteItemRequest = getFavoriteItemRequest()
        viewModelScope.launch {
            if (state.value.favoriteItem.size != 20) {
                toggleFavoriteItemUseCase(favoriteItemRequest.toDomain())
                    .onSuccess {
                        getFavorites(uid)
                    }
                    .onFailure {
                        emitSideEffect(
                            ShowSnackBar(it.toStringResId())
                        )
                    }
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


    private fun addRecentlyItem() {
        val recentlyItemRequest = getRecentlyItemRequest()
        viewModelScope.launch {
            getRecentlyUseCase(state.value.user.uid)
                .onSuccess { recentlyViewedDomain ->
                    updateState { copy(recentlyItemsId = recentlyViewedDomain.map { it.itemId }) }
                    if (
                        !state.value.recentlyItemsId.contains(recentlyItemRequest.itemId) &&
                        recentlyViewedDomain.size != 20
                    ) {
                        addRecentlyItemUseCase(recentlyItemRequest.toDomain())
                    }
                }
        }
    }

    private fun addItemToCart() {
        val cartItemRequest = getCartItemRequest()
        viewModelScope.launch {
            if (state.value.cartItemIds.size != 20) {
                addItemToCartUseCase(cartItemRequest.toDomain())
                    .onSuccess {
                        if (!state.value.isInCart) {
                            updateState { copy(isInCart = true) }
                        }
                    }
            }
        }
    }

    private fun getCartItemIds() {
        viewModelScope.launch {
            getCartItemsUseCase(state.value.user.uid)
                .onSuccess { cartItemIds ->
                    updateState { copy(cartItemIds = cartItemIds.map { it.itemId }) }
                    checkInCart()
                }
        }
    }

    private fun checkInCart() = with(state.value) {
        if (cartItemIds.contains(itemId)) {
            updateState { copy(isInCart = true) }
        } else {
            updateState { copy(isInCart = false) }
        }
    }

    private fun navigateBackToFeed() {
        emitSideEffect(NavigateBackToFeed)
    }

    private fun getItemDetails(id: Int) = viewModelScope.launch {
        updateState { copy(isLoading = true) }

        getItemDetailsUseCase(id)
            .onSuccess { itemDetailsDomain ->
                updateState { copy(itemDetails = itemDetailsDomain.toPresentation()) }
                updateState { copy(isLoading = false) }
                getSeller()
            }
            .onFailure {
                emitSideEffect(ShowSnackBar(errorRes = it.toStringResId()))
                updateState { copy(isLoading = false) }
            }
    }

    private fun getRecentlyItemRequest(): UiRecentlyRequest {
        return UiRecentlyRequest(
            uid = state.value.user.uid,
            itemId = state.value.itemId,
        )
    }

    private fun getCartItemRequest(): UiCartItemRequest {
        return UiCartItemRequest(
            uid = state.value.user.uid,
            itemId = state.value.itemId,
        )
    }

    private fun getFavoriteItemRequest(): UiFavoriteItemRequest {
        return UiFavoriteItemRequest(
            uid = state.value.user.uid,
            itemId = state.value.itemId,
            favorites = state.value.favoriteItem.map { it.toDomain() }
        )
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            val currentUser = getCurrentUserUseCase()?.toPresentation()
            currentUser?.let {
                updateState { copy(user = currentUser) }
            }
        }
    }

    private fun getSeller() = viewModelScope.launch {
        state.value.itemDetails?.let { itemDetails ->
            getSellersUseCase(itemDetails.uid)
                .onSuccess { sellers ->
                    val seller = sellers.map { it.toPresentation() }.firstOrNull()

                    seller?.let {
                        updateState { copy(seller = seller) }
                    }
                }
                .onFailure {
                    d("asdd", "failure message getSeller() $it")
                }
        }

    }

}