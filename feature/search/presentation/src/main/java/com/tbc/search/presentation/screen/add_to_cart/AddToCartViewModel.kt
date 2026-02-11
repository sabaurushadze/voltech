package com.tbc.search.presentation.screen.add_to_cart

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
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
    private val getItemsByIdsUseCase: GetItemsByIdsUseCase
) : BaseViewModel<AddToCartState, Unit, AddToCartEvent>(AddToCartState()){

    override fun onEvent(event: AddToCartEvent) {
        when(event){
            AddToCartEvent.GetCartItems -> getCartItemIds()
        }
    }

    private fun getCartItemIds(){
        viewModelScope.launch {
            val user = getCurrentUserUseCase()
            user?.let { user ->
                getCartItemsUseCase(user.uid)
                    .onSuccess { cartItemIds ->
                        updateState { copy(cartItemIds = cartItemIds.map { it.itemId }) }
                        getCartItems()
                    }
            }
        }
    }

    private fun getCartItems(){
        viewModelScope.launch {
            getItemsByIdsUseCase(state.value.cartItemIds)
                .onSuccess { cartItems ->
                    updateState { copy( cartItems = cartItems.toPresentation()) }
                }
        }
    }
}