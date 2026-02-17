package com.tbc.selling.domain.usecase.selling.add_item.add_seller

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.selling.domain.model.SellerRequest
import com.tbc.selling.domain.repository.SellerRepository
import javax.inject.Inject

class AddSellerUseCase @Inject constructor(
    private val sellerRepository: SellerRepository,
) {
    suspend operator fun invoke(seller: SellerRequest): Resource<Unit, DataError.Network> {
        return sellerRepository.addSeller(seller)
    }
}
