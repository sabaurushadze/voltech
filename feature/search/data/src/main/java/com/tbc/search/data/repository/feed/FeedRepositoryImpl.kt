package com.tbc.search.data.repository.feed

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tbc.core.data.remote.util.ApiResponseHandler
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core.domain.util.map
import com.tbc.core.domain.util.mapList
import com.tbc.search.data.mapper.feed.toDomain
import com.tbc.search.data.mapper.feed.toDto
import com.tbc.search.data.paging.feed.FeedPagingSource
import com.tbc.search.data.service.feed.FeedService
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.model.feed.FeedQuery
import com.tbc.search.domain.model.feed.Item
import com.tbc.search.domain.repository.feed.FeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class FeedRepositoryImpl @Inject constructor(
    private val feedService: FeedService,
    private val responseHandler: ApiResponseHandler,
) : FeedRepository {
    override fun getFeedItemsPaging(
        query: FeedQuery,
        pageSize: Int,
    ): Flow<PagingData<FeedItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
            ),
            pagingSourceFactory = { FeedPagingSource(service = feedService, query = query) }
        ).flow
    }

    override suspend fun getItemDetails(id: Int): Resource<FeedItem, DataError.Network> {
        return responseHandler.safeApiCall {
            feedService.getItemDetails(id)
        }.map { it.toDomain() }
    }

    override suspend fun getItemsByIds(ids: List<Int>): Resource<List<FeedItem>, DataError.Network> {
        return responseHandler.safeApiCall {
            feedService.getItemsByIds(ids)
        }.map { dto ->
            val domainItems = dto.map { it.toDomain() }
            val itemsMap = domainItems.associateBy { it.id }
            ids.mapNotNull { id -> itemsMap[id] }
        }
    }

    override suspend fun getItemsByUid(uid: String): Resource<List<FeedItem>, DataError.Network> {
        return responseHandler.safeApiCall {
            feedService.getItemsByUid(uid)
        }.mapList { it.toDomain() }
    }

    override suspend fun addItem(item: Item): Resource<Unit, DataError.Network> {
        val itemRequestDto = item.toDto()
        return responseHandler.safeApiCall {
            feedService.addItem(itemRequestDto)
        }
    }

    override suspend fun deleteItem(id: Int): Resource<Unit, DataError.Network> {
        return responseHandler.safeApiCall {
            feedService.deleteItem(id)
        }
    }
}