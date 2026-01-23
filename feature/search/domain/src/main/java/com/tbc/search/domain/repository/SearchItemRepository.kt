package com.tbc.search.domain.repository

import com.tbc.search.domain.model.SearchItem
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource

interface SearchItemRepository {
    suspend fun searchItem(query: String): Resource<List<SearchItem>, DataError.Network>
}