package com.tbc.search.presentation.screen.add_to_cart

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.user.toPresentation
import com.tbc.search.domain.usecase.cart.DeleteCartItemUseCase
import com.tbc.search.domain.usecase.cart.GetCartItemsUseCase
import com.tbc.search.domain.usecase.feed.GetItemsByIdsUseCase
import com.tbc.search.presentation.mapper.cart.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddToCartViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getItemsByIdsUseCase: GetItemsByIdsUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase,
) : BaseViewModel<AddToCartState, AddToCartSideEffect, AddToCartEvent>(AddToCartState()) {

    init {
        getCurrentUser()
    }

    override fun onEvent(event: AddToCartEvent) {
        when (event) {
            is AddToCartEvent.DeleteCartItems -> deleteFavoriteItemIds(event.cartId)
            AddToCartEvent.GetCartItems -> getCartItemIds()
            AddToCartEvent.BuyItem -> emitSideEffect(AddToCartSideEffect.ShowSnackBar(com.tbc.resource.R.string.this_service_not_work))
        }
    }

    private fun getCartItemIds() = viewModelScope.launch {
        updateState { copy(isLoading = true) }

        state.value.user?.let { user ->
            getCartItemsUseCase(user.uid)
                .onSuccess { cartItems ->

                    val itemIds = cartItems.map { it.itemId }

                    getItemsByIdsUseCase(itemIds).onSuccess { items ->

                        val uiCartItems = items.map { item ->
                            val cartItem = cartItems.first { it.itemId == item.id }

                            item.toPresentation(
                                cartId = cartItem.id
                            )
                        }

                        updateState {
                            copy(
                                cartItems = uiCartItems,
                                isLoading = false,
                                showNoConnectionError = false
                            )
                        }
                        calculateSubTotal(items.map { it.price })

                    }.onFailure { result ->
                        if (result == DataError.Network.NO_CONNECTION) {
                            updateState { copy(isLoading = false, showNoConnectionError = true) }
                        } else {
                            updateState { copy(isLoading = false, showNoConnectionError = false) }
                        }
                    }
                }.onFailure { result ->
                    if (result == DataError.Network.NO_CONNECTION) {
                        updateState { copy(isLoading = false, showNoConnectionError = true) }
                    } else {
                        updateState { copy(isLoading = false, showNoConnectionError = false) }
                    }
                }
        }

    }



    private fun getCurrentUser() = viewModelScope.launch {
        val currentUser = getCurrentUserUseCase()?.toPresentation()
        updateState { copy(user = currentUser) }
    }

    private fun deleteFavoriteItemIds(cartId: Int) = viewModelScope.launch {
        deleteCartItemUseCase(cartId)
        getCartItemIds()
    }

    private fun calculateSubTotal(priceList: List<Double>) {
        val subtotal = priceList.sum()
        updateState {
            copy(subtotal = subtotal)
        }
    }
}