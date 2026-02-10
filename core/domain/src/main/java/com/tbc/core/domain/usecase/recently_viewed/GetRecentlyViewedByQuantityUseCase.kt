package com.tbc.core.domain.usecase.recently_viewed

import com.tbc.core.domain.model.recently_viewed.Recently
import com.tbc.core.domain.repository.recently_viewed.RecentlyRepository
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import javax.inject.Inject

class GetRecentlyViewedByQuantityUseCase @Inject constructor(
    private val repository: RecentlyRepository
){
    suspend operator fun invoke(uid: String): Resource<List<Recently>, DataError.Network>{
        return repository.getRecentlyViewedByQuantity(uid)
    }
}