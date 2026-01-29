package com.tbc.search.domain.usecase.feed

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.repository.feed.FeedRepository
import javax.inject.Inject

class GetItemDetailsUseCase @Inject constructor(
    private val feedRepository: FeedRepository,
) {
    suspend operator fun invoke(id: Int): Resource<FeedItem, DataError.Network> {
        return feedRepository.getItemDetails(id)
    }
}
