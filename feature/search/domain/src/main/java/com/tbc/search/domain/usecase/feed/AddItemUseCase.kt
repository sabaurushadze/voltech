package com.tbc.search.domain.usecase.feed

import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.Resource
import com.tbc.search.domain.model.feed.Item
import com.tbc.search.domain.repository.feed.FeedRepository
import javax.inject.Inject

class AddItemUseCase @Inject constructor(
    private val feedRepository: FeedRepository,
) {
    suspend operator fun invoke(item: Item): Resource<Unit, DataError.Network> {
        return feedRepository.addItem(item)
    }
}