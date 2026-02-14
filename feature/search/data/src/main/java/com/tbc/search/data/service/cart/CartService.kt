package com.tbc.search.data.service.cart

import com.tbc.search.data.dto.cart.CartItemRequestDto
import com.tbc.search.data.dto.cart.CartResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CartService {
    @GET(CART)
    suspend fun getCartItems(
        @Query(UID) uid: String,
        @Query(SORT) sort: String = ADDED_AT,
        @Query(ORDER) order: String = DESC,
    ): Response<List<CartResponseDto>>

    @POST(CART)
    suspend fun addItemToCart(
        @Body request: CartItemRequestDto
    ): Response<Unit>

    @DELETE("cart/{id}")
    suspend fun deleteCartItem(
        @Path("id") id: Int
    ): Response<Unit>

    companion object{
        const val CART = "cart"
        private const val UID = "uid"
        private const val SORT = "_sort"
        private const val ORDER = "_order"
        private const val ADDED_AT = "addedAt"
        private const val DESC = "desc"
    }
}