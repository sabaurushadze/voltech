package com.tbc.search.data.repository.search

import com.tbc.core.data.remote.util.ApiResponseHandler
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core.domain.util.mapList
import com.tbc.search.data.mapper.search.toDomain
import com.tbc.search.data.service.search.SearchService
import com.tbc.search.domain.model.search.SearchItem
import com.tbc.search.domain.repository.search.SearchItemRepository
import javax.inject.Inject

class SearchItemRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val searchItemService: SearchService,
) : SearchItemRepository {
    override suspend fun searchItem(query: String): Resource<List<SearchItem>, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            searchItemService.searchItemsByQuery(query)
        }.mapList { it.toDomain() }
    }
}