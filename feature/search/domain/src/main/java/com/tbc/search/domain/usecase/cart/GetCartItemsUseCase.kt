package com.tbc.search.domain.usecase.cart

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.cart.Cart
import com.tbc.search.domain.repository.cart.CartRepository
import javax.inject.Inject

class GetCartItemsUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(uid: String): Resource<List<Cart>, DataError.Network>{
        return repository.getCartItems(uid)
    }
}