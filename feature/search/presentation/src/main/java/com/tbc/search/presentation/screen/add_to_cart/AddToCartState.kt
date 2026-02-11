package com.tbc.search.presentation.screen.add_to_cart

import com.tbc.search.presentation.model.cart.UiCartItem


data class AddToCartState (
    val isLoading: Boolean = false,
    val cartItemIds: List<Int> = emptyList(),
    val cartItems: List<UiCartItem> = emptyList()
)