package com.tbc.search.domain.usecase.feed

import androidx.paging.PagingData
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.model.feed.FeedQuery
import com.tbc.search.domain.repository.feed.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFeedItemsPagingUseCase @Inject constructor(
    private val feedRepository: FeedRepository
) {
    operator fun invoke(query: FeedQuery, pageSize: Int): Flow<PagingData<FeedItem>> {
        return feedRepository.getFeedItemsPaging(
            query = query,
            pageSize = pageSize,
        )
    }
}