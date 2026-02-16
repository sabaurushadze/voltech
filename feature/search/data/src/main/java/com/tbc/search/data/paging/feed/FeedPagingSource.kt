package com.tbc.search.data.paging.feed

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tbc.search.data.mapper.feed.toDomain
import com.tbc.search.data.service.feed.FeedService
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.model.feed.FeedQuery
import kotlinx.coroutines.delay
import javax.inject.Inject

internal class FeedPagingSource @Inject constructor(
    private val service: FeedService,
    private val query: FeedQuery,

    ) : PagingSource<Int, FeedItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FeedItem> {
        return try {
            val page = params.key ?: 1

//            delay because it loads so fast we cant see loading animations
            delay(500)

            val order = if (query.sortDescending) DESC else ASC

            val response = service.getItemsWithPagination(
                query = query.titleLike,
                category = query.category,
                minPrice = query.minPrice,
                maxPrice = query.maxPrice,
                location = query.location,
                condition = query.condition,
                sortBy = query.sortBy,
                order = order,
                page = page,
                perPage = PER_PAGE_COUNT,
            )

            if (response.isSuccessful) {
                val items = response.body() ?: emptyList()
                LoadResult.Page(
                    data = items.map { it.toDomain() },
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (items.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Exception("HTTP error ${response.code()}"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FeedItem>): Int? {
        return state.anchorPosition?.let { anchor ->
            val page = state.closestPageToPosition(anchor)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }

    companion object {
        const val PER_PAGE_COUNT = 10
        private const val DESC = "desc"
        private const val ASC = "asc"
    }

}