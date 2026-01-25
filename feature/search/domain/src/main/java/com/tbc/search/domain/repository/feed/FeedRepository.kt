package com.tbc.search.domain.repository.feed

import androidx.paging.PagingData
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.model.feed.FeedQuery
import kotlinx.coroutines.flow.Flow


interface FeedRepository {
    fun getFeedItemsPaging(
        query: FeedQuery,
        pageSize: Int,
    ): Flow<PagingData<FeedItem>>
}