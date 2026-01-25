package com.tbc.search.presentation.screen.search

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.datastore.preference_keys.VoltechPreferenceKeys
import com.tbc.core.domain.datastore.usecase.AddToSetPreferenceUseCase
import com.tbc.core.domain.datastore.usecase.GetSetPreferenceUseCase
import com.tbc.core.domain.datastore.usecase.RemoveFromSetPreferenceUseCase
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.toStringResId
import com.tbc.search.domain.usecase.SearchItemByQueryUseCase
import com.tbc.search.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchItemByQueryUseCase: SearchItemByQueryUseCase,
    private val addToSetPreferenceUseCase: AddToSetPreferenceUseCase,
    private val getSetPreferenceUseCase: GetSetPreferenceUseCase,
    private val removeFromSetPreferenceUseCase: RemoveFromSetPreferenceUseCase
) : BaseViewModel<SearchState, SearchSideEffect, SearchEvent>(SearchState()) {

    private val queryFlow = MutableStateFlow("")

    init {
        observeQuery()
        fetchRecentSearches()
    }

    override fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.QueryChanged -> updateQuery(event.query)
            is SearchEvent.SearchByQuery -> searchByQuery(event.query)
            is SearchEvent.NavigateToFeedWithQuery -> navigateToFeedWithQuery(event.query)
            is SearchEvent.SaveRecentSearch -> saveRecentSearch(event.query)
            is SearchEvent.RemoveRecentSearch -> removeRecentSearch(event.query)
        }
    }

    private fun navigateToFeedWithQuery(query: String) {
        emitSideEffect(SearchSideEffect.NavigateToFeed(query))
    }

    private fun saveRecentSearch(query: String){
        viewModelScope.launch {
            addToSetPreferenceUseCase(
                VoltechPreferenceKeys.RECENT_SEARCHES,
                query
            )
        }
    }

    private fun fetchRecentSearches(){
        viewModelScope.launch {
            getSetPreferenceUseCase(
                VoltechPreferenceKeys.RECENT_SEARCHES
            ).collectLatest {
                updateState { copy(recentSearchList = it) }
            }
        }
    }

    private fun removeRecentSearch(query: String){
        viewModelScope.launch {
            removeFromSetPreferenceUseCase(
                VoltechPreferenceKeys.RECENT_SEARCHES,
                query
            )
        }
    }

//    uketesi gza tu arsebobs sanaxavia
    @OptIn(FlowPreview::class)
    private fun observeQuery() {
        viewModelScope.launch {
            queryFlow
                .debounce(300)
                .collectLatest { query ->
                    if (query.isBlank()) {
                        updateState { copy(titles = emptyList()) }
                    } else {
                        searchByQuery(query)
                    }
                }
        }
    }




    private fun updateQuery(query: String) {
        updateState { copy(query = query) }
        queryFlow.value = query
    }

    private fun searchByQuery(query: String) = viewModelScope.launch {
        updateState { copy(isLoading = true) }

        searchItemByQueryUseCase(query)
            .onSuccess { titlesDomain ->
                updateState { copy(titles = titlesDomain.map { it.toPresentation() }) }
                updateState { copy(isLoading = false) }
            }
            .onFailure {
                emitSideEffect(SearchSideEffect.ShowSnackBar(errorRes = it.toStringResId()))
                updateState { copy(isLoading = false) }
            }
    }
}