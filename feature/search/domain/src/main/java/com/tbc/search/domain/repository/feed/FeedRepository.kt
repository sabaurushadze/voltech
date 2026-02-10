package com.tbc.search.domain.repository.feed

import androidx.paging.PagingData
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.model.feed.FeedQuery
import com.tbc.search.domain.model.feed.Item
import kotlinx.coroutines.flow.Flow


interface FeedRepository {
    fun getFeedItemsPaging(
        query: FeedQuery,
        pageSize: Int,
    ): Flow<PagingData<FeedItem>>

    suspend fun getItemDetails(id: Int) : Resource<FeedItem, DataError.Network>

    suspend fun getItemsByIds(ids: List<Int>): Resource<List<FeedItem>, DataError.Network>
    suspend fun getItemsByUid(uid: String): Resource<List<FeedItem>, DataError.Network>
    suspend fun addItem(item: Item): Resource<Unit, DataError.Network>
}