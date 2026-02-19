package com.tbc.search.presentation.screen.feed

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.tbc.core.domain.model.category.Category
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.Location
import com.tbc.search.domain.usecase.feed.GetFeedItemsPagingUseCase
import com.tbc.search.presentation.enums.feed.SortType
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

            is FeedEvent.SaveSearchQuery -> saveSearchQuery(event.query)
            is FeedEvent.SaveCategoryQuery -> saveCategoryQuery(event.category)
            FeedEvent.HideSortSheet -> hideSortBottomSheet()
            FeedEvent.ShowSortSheet -> showSortBottomSheet()
            is FeedEvent.SelectSortType -> selectSortType(event.sortType)

            FeedEvent.HideFilterSheet -> hideFilterBottomSheet()
            FeedEvent.ShowFilterSheet -> showFilterBottomSheet()

            is FeedEvent.UpdateMinPrice -> updateMinPrice(event.value)
            is FeedEvent.UpdateMaxPrice -> updateMaxPrice(event.value)
            is FeedEvent.FilterItems -> applyFilters(event.currentQuery)
            is FeedEvent.ToggleCategory -> {
                toggleCategory(
                    category = event.category,
                    isSelected = event.selected
                )
            }

            is FeedEvent.ToggleCondition -> {
                toggleCondition(
                    condition = event.condition,
                    isSelected = event.selected
                )
            }

            is FeedEvent.ToggleLocation -> {
                toggleLocation(
                    location = event.location,
                    isSelected = event.selected
                )
            }

            is FeedEvent.FeedItemClick -> navigateToDetails(event.id)
            is FeedEvent.GetSellerItemsByUid -> getSellerItemsByUid(event.sellerUid)
        }
    }

    private fun navigateToDetails(id: Int) {
        emitSideEffect(FeedSideEffect.NavigateToItemDetails(id))
    }

    private fun toggleLocation(location: Location, isSelected: Boolean) {
        updateState {
            val newSet = filterState.selectedLocations.toMutableSet()
            if (isSelected) newSet.add(location) else newSet.remove(location)
            copy(filterState = filterState.copy(selectedLocations = newSet))
        }
    }

    private fun toggleCondition(condition: Condition, isSelected: Boolean) {
        updateState {
            val newSet = filterState.selectedConditions.toMutableSet()
            if (isSelected) newSet.add(condition) else newSet.remove(condition)
            copy(filterState = filterState.copy(selectedConditions = newSet))
        }
    }

    private fun toggleCategory(category: Category, isSelected: Boolean) {
        updateState {
            val newSet = filterState.selectedCategories.toMutableSet()
            if (isSelected) newSet.add(category) else newSet.remove(category)
            copy(filterState = filterState.copy(selectedCategories = newSet))
        }
    }

    private fun updateMinPrice(value: String) {
        updateState { copy(filterState = filterState.copy(minPrice = value)) }
    }

    private fun updateMaxPrice(value: String) {
        updateState { copy(filterState = filterState.copy(maxPrice = value)) }
    }

    private fun applyFilters(currentQuery: String) {
        val filter = state.value.filterState
        updateState {
            val updatedQuery = query.copy(
                titleLike = currentQuery.ifBlank { null },
                category = filter.selectedCategories.takeIf { it.isNotEmpty() }?.map { it.name },
                condition = filter.selectedConditions.takeIf { it.isNotEmpty() }?.map { it.name },
                location = filter.selectedLocations.takeIf { it.isNotEmpty() }?.map { it.name },
                minPrice = filter.minPrice.toFloatOrNull(),
                maxPrice = filter.maxPrice.toFloatOrNull(),
            )

            copy(
                query = updatedQuery,
                selectedFilter = false
            )
        }
    }

    private fun selectSortType(sortType: SortType) {
        updateState {
            val updatedQuery = query.copy(
                sortBy = PRICE,
                sortDescending = sortType == SortType.PRICE_HIGHEST
            )
            copy(
                selectedSortType = sortType,
                selectedSort = false,
                query = updatedQuery
            )
        }
    }

    private fun getSellerItemsByUid(sellerUid: String) {
        updateState {
            copy(
                query = query.copy(
                    titleLike = null,
                    category = null,
                    uid = sellerUid
                )
            )
        }
    }


    private fun saveCategoryQuery(category: String) {
        val categoryEnum = Category.fromString(category)

        updateState {
            if (initialCategoryConsumed) return@updateState this

            val updatedQuery = query.copy(
                titleLike = null,
                category = listOf(categoryEnum.name)
            )

            copy(
                query = updatedQuery,
                filterState = filterState.copy(
                    selectedCategories = setOf(categoryEnum)
                ),
                initialCategoryConsumed = true
            )
        }
    }


    private fun saveSearchQuery(searchQuery: String) {
        updateState {
            val initQuery = query.copy(
                sortBy = "price",
                sortDescending = false,
                titleLike = searchQuery
            )
            copy(
                query = initQuery
            )
        }
    }

    private fun showSortBottomSheet() {
        updateState { copy(selectedSort = true) }
    }

    private fun hideSortBottomSheet() {
        updateState { copy(selectedSort = false) }
    }

    private fun showFilterBottomSheet() {
        updateState { copy(selectedFilter = true) }
    }

    private fun hideFilterBottomSheet() {
        updateState { copy(selectedFilter = false) }
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

            }
            .cachedIn(viewModelScope)


    companion object {
        private const val PAGE_SIZE = 10
        private const val PRICE = "price"

    }
}