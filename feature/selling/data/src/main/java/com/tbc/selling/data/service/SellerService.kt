package com.tbc.selling.data.service

import com.tbc.selling.data.dto.patch.SellerProfilePatchDto
import com.tbc.selling.data.dto.patch.SellerRatingPatchDto
import com.tbc.selling.data.dto.request.SellerRequestDto
import com.tbc.selling.data.dto.response.SellerResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface SellerService {
    @POST(SELLERS)
    suspend fun addSeller(
        @Body userRequestDto: SellerRequestDto,
    ): Response<Unit>

    @GET(SELLERS)
    suspend fun getSellersByUid(
        @Query(UID) uid: String,
    ): Response<List<SellerResponseDto>>

    @PATCH("$SELLERS/{id}")
    suspend fun updateSellerProfile(
        @Path("id") id: Int,
        @Body sellerProfilePatchDto: SellerProfilePatchDto,
    ): Response<Unit>

    @PATCH("$SELLERS/{id}")
    suspend fun updateSellerRating(
        @Path("id") id: Int,
        @Body sellerRatingPatchDto: SellerRatingPatchDto,
    ): Response<Unit>

    companion object {
        private const val SELLERS = "sellers"
        private const val UID = "uid"
    }
}

