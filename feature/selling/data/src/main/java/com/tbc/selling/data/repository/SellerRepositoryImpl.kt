package com.tbc.selling.data.repository

import com.tbc.core.data.remote.util.ApiResponseHandler
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core.domain.util.mapList
import com.tbc.selling.data.mapper.toDomain
import com.tbc.selling.data.mapper.toDto
import com.tbc.selling.data.service.SellerService
import com.tbc.selling.domain.model.SellerProfile
import com.tbc.selling.domain.model.SellerRating
import com.tbc.selling.domain.model.SellerRequest
import com.tbc.selling.domain.model.SellerResponse
import com.tbc.selling.domain.repository.SellerRepository
import javax.inject.Inject

internal class SellerRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val sellerService: SellerService,
) : SellerRepository {

    override suspend fun addSeller(seller: SellerRequest): Resource<Unit, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            val sellerDto = seller.toDto()
            sellerService.addSeller(sellerDto)
        }
    }

    override suspend fun getSellers(uid: String): Resource<List<SellerResponse>, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            sellerService.getSellersByUid(uid)
        }.mapList { it.toDomain() }
    }

    override suspend fun updateSellerProfile(sellerProfile: SellerProfile): Resource<Unit, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            val updatedSellerProfile = sellerProfile.toDto()
            sellerService.updateSellerProfile(sellerProfile.id, updatedSellerProfile)
        }
    }

    override suspend fun updateSellerRating(sellerRating: SellerRating): Resource<Unit, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            val updatedSellerRating = sellerRating.toDto()
            sellerService.updateSellerRating(sellerRating.id, updatedSellerRating)
        }
    }
}