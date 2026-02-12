package com.tbc.search.domain.usecase.cart

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.cart.CartItemRequest
import com.tbc.search.domain.repository.cart.CartRepository
import javax.inject.Inject

class AddItemToCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke(cartItem: CartItemRequest) : Resource<Unit, DataError.Network> {
        return repository.addItemToCart(cartItem)
    }
}