package com.tbc.search.domain.usecase.feed

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.feed.FeedItem
import com.tbc.search.domain.repository.feed.FeedRepository
import javax.inject.Inject

class GetItemsByIdsUseCase @Inject constructor(
    private val repository: FeedRepository
) {
    suspend operator fun invoke(ids: List<Int>): Resource<List<FeedItem>, DataError.Network>{
        return repository.getItemsByIds(ids)
    }
}