package com.tbc.search.domain.repository.cart

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.cart.Cart
import com.tbc.search.domain.model.cart.CartItemRequest

interface CartRepository {
    suspend fun getCartItems(uid: String): Resource<List<Cart>, DataError.Network>

    suspend fun addItemToCart(cartItem: CartItemRequest): Resource<Unit, DataError.Network>
}