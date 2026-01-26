package com.tbc.search.presentation.screen.feed

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.search.domain.model.feed.FeedQuery
import com.tbc.search.domain.usecase.feed.GetFeedItemsPagingUseCase
import com.tbc.search.presentation.mapper.feed.toPresentation
import com.tbc.search.presentation.model.feed.UiFeedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFeedItemsPagingUseCase: GetFeedItemsPagingUseCase,
) : BaseViewModel<FeedState, FeedSideEffect, FeedEvent>(FeedState()) {

    override fun onEvent(event: FeedEvent) {
        when (event) {
            is FeedEvent.GetItems -> {
            }

            is FeedEvent.SaveSearchQuery -> saveSearchQuery(event.query)
        }
    }

    private fun saveSearchQuery(searchQuery: String) {
        updateState {
            copy(
                query = FeedQuery(
                    titleLike = searchQuery,
                )
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val feedPagingFlow: Flow<PagingData<UiFeedItem>> =
        state
            .map { it.query }
            .distinctUntilChanged()
            .flatMapLatest { query ->
                getFeedItemsPagingUseCase(
                    pageSize = PAGE_SIZE,
                    query = query
                )
                    .map { pagingData ->
                        pagingData.map { domainFeedItem ->
                            domainFeedItem.toPresentation()
                        }
                    }
//                    .cachedIn(viewModelScope)
            }

    companion object {
        private const val PAGE_SIZE = 10

    }
}