package com.tbc.search.data.repository.cart

import com.tbc.core.data.remote.util.ApiResponseHandler
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core.domain.util.mapList
import com.tbc.search.data.mapper.cart.toData
import com.tbc.search.data.mapper.cart.toDomain
import com.tbc.search.data.service.cart.CartService
import com.tbc.search.domain.model.cart.Cart
import com.tbc.search.domain.model.cart.CartItemRequest
import com.tbc.search.domain.repository.cart.CartRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

internal class CartRepositoryImpl @Inject constructor(
    private val cartService: CartService,
    private val apiResponseHandler: ApiResponseHandler
): CartRepository {
    override suspend fun getCartItems(uid: String): Resource<List<Cart>, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            delay(500)
            cartService.getCartItems(uid)
        }.mapList { it.toDomain() }
    }

    override suspend fun addItemToCart(cartItem: CartItemRequest): Resource<Unit, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            cartService.addItemToCart(cartItem.toData())
        }
    }

    override suspend fun deleteCartItem(id: Int): Resource<Unit, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            cartService.deleteCartItem(id)
        }
    }
}