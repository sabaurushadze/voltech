package com.tbc.search.domain.usecase.feed

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.feed.ItemStatus
import com.tbc.search.domain.repository.feed.FeedRepository
import javax.inject.Inject

class UpdateItemStatusUseCase @Inject constructor(
    private val feedRepository: FeedRepository,
) {
    suspend operator fun invoke(id: Int, itemStatus: ItemStatus): Resource<Unit, DataError.Network> {
        return feedRepository.updateItemStatus(id = id, itemStatus = itemStatus)
    }
}