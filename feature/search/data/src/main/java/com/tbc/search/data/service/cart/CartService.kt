package com.tbc.search.data.service.cart

import com.tbc.search.data.dto.cart.CartResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CartService {
    @GET(CART)
    suspend fun getCartItems(
        @Query(UID) uid: String,
        @Query(SORT) sort: String = VIEWED_AT,
        @Query(ORDER) order: String = DESC,
    ): Response<List<CartResponseDto>>


    companion object{
        const val CART = "cart"
        private const val UID = "uid"
        private const val SORT = "_sort"
        private const val ORDER = "_order"
        private const val VIEWED_AT = ""
        private const val DESC = "desc"
    }
}