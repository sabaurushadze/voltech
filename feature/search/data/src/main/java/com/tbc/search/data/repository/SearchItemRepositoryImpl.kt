package com.tbc.search.data.repository

import com.tbc.core.data.remote.util.ApiResponseHandler
import com.tbc.search.domain.model.SearchItem
import com.tbc.search.domain.repository.SearchItemRepository
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.core.domain.util.mapList
import com.tbc.search.data.mapper.toDomain
import com.tbc.search.data.service.SearchItemService
import javax.inject.Inject

class SearchItemRepositoryImpl @Inject constructor(
    private val apiResponseHandler: ApiResponseHandler,
    private val searchItemService: SearchItemService,
) : SearchItemRepository {
    override suspend fun searchItem(query: String): Resource<List<SearchItem>, DataError.Network> {
        return apiResponseHandler.safeApiCall {
            searchItemService.searchItemsByQuery(query)
        }.mapList { it.toDomain() }
    }
}