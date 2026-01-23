package com.tbc.search.domain.usecase

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.SearchItem
import com.tbc.search.domain.repository.SearchItemRepository
import javax.inject.Inject

class SearchItemByQueryUseCase @Inject constructor(
    private val searchItemRepository: SearchItemRepository
) {
    suspend operator fun invoke(query: String): Resource<List<SearchItem>, DataError.Network> {
        return searchItemRepository.searchItem(query)
    }
}