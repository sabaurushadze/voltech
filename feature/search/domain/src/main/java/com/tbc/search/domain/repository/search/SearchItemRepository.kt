package com.tbc.search.domain.repository.search

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.search.SearchItem

interface SearchItemRepository {
    suspend fun searchItem(query: String): Resource<List<SearchItem>, DataError.Network>
}