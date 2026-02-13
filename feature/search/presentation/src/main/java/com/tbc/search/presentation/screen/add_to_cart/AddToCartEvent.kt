package com.tbc.search.presentation.screen.add_to_cart

sealed class AddToCartEvent {
    data object GetCartItems: AddToCartEvent()
    data class DeleteCartItems(val cartId: Int): AddToCartEvent()
    data object BuyItem: AddToCartEvent()
}