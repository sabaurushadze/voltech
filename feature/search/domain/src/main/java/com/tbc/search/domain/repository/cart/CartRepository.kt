package com.tbc.search.domain.repository.cart

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.cart.Cart

interface CartRepository {
    suspend fun getCartItems(uid: String): Resource<List<Cart>, DataError.Network>
}