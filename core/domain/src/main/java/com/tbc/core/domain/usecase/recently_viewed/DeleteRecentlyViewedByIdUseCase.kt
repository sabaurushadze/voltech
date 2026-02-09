package com.tbc.core.domain.usecase.recently_viewed

import com.tbc.core.domain.repository.recently_viewed.RecentlyRepository
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import javax.inject.Inject

class DeleteRecentlyViewedByIdUseCase @Inject constructor(
    private val repository: RecentlyRepository
) {
    suspend operator fun invoke(id: Int): Resource<Unit, DataError.Network> {
        return repository.deleteRecentlyViewedById(id)
    }
}