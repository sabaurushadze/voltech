package com.tbc.selling.domain.repository

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.selling.domain.model.SellerProfile
import com.tbc.selling.domain.model.SellerRating
import com.tbc.selling.domain.model.SellerRequest
import com.tbc.selling.domain.model.SellerResponse

interface SellerRepository {
    suspend fun addSeller(seller: SellerRequest): Resource<Unit, DataError.Network>
    suspend fun getSellers(uid: String): Resource<List<SellerResponse>, DataError.Network>

    suspend fun updateSellerProfile(sellerProfile: SellerProfile): Resource<Unit, DataError.Network>
    suspend fun updateSellerRating(sellerRating: SellerRating): Resource<Unit, DataError.Network>
}