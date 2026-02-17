package com.tbc.selling.domain.usecase.selling.add_item.add_seller

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.selling.domain.model.SellerResponse
import com.tbc.selling.domain.repository.SellerRepository
import javax.inject.Inject

class GetSellersUseCase @Inject constructor(
    private val sellerRepository: SellerRepository,
) {
    suspend operator fun invoke(uid: String): Resource<List<SellerResponse>, DataError.Network> {
        return sellerRepository.getSellers(uid)
    }
}
