package com.tbc.search.presentation.screen.add_to_cart

import com.tbc.core.presentation.model.UiUser
import com.tbc.search.presentation.model.cart.UiCartItem
import com.tbc.search.presentation.model.item_details.UiSeller


data class AddToCartState(
    val showNoConnectionError: Boolean = false,
    val user: UiUser? = null,
    val isLoading: Boolean = true,
    val cartItemIds: List<Int> = emptyList(),
    val cartItems: List<UiCartItem> = emptyList(),
    val subtotal: Double = 0.0,
    val sellers: List<UiSeller> = emptyList(),
)