package com.tbc.home.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.recently_viewed.GetRecentlyViewedByQuantityUseCase
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.home.domain.usecase.GetCategoriesUseCase
import com.tbc.home.presentation.mapper.category.toPresentation
import com.tbc.home.presentation.mapper.recently_viewed.toPresentation
import com.tbc.search.domain.usecase.feed.GetItemsByIdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getRecentlyViewedByQuantityUseCase: GetRecentlyViewedByQuantityUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getItemsByIdsUseCase: GetItemsByIdsUseCase,
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()) {

    override fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetCategories -> getCategories()
            HomeEvent.GetRecentlyViewedItems -> getRecentlyIds()
        }
    }


    private fun getCategories() {
        updateState { copy(isLoadingCategories = true) }

        viewModelScope.launch {
            getCategoriesUseCase()
                .onSuccess { categoryDomain ->
                    updateState {
                        copy(
                            categoryList = categoryDomain.toPresentation(),
                            isLoadingCategories = false,
                            showNoConnectionError = false
                        )
                    }
                }
                .onFailure { result ->
                    if (result == DataError.Network.NO_CONNECTION) {
                        updateState {
                            copy(
                                isLoadingCategories = false,
                                showNoConnectionError = true
                            )
                        }

                    } else {
                        updateState {
                            copy(
                                isLoadingCategories = false,
                                showNoConnectionError = false
                            )
                        }
                    }
                }
        }
    }

    private fun getRecentlyIds() {
        updateState { copy(isLoadingRecentlyViewed = true) }
        val user = getCurrentUserUseCase()
        user?.let { user ->
            viewModelScope.launch {
                getRecentlyViewedByQuantityUseCase(user.uid)
                    .onSuccess { recentlyViewedDomain ->
                        val ids = recentlyViewedDomain.map { it.itemId }
                        updateState { copy(recentlyItemsId = ids) }
                        getRecentlyViewedByIds(ids)
                    }
                    .onFailure { result ->
                        if (result == DataError.Network.NO_CONNECTION) {
                            updateState {
                                copy(
                                    isLoadingRecentlyViewed = false,
                                    showNoConnectionError = true
                                )
                            }

                        } else {
                            updateState {
                                copy(
                                    isLoadingRecentlyViewed = false,
                                    showNoConnectionError = false
                                )
                            }
                        }
                    }
            }
        }
    }

    private fun getRecentlyViewedByIds(ids: List<Int>) {
        viewModelScope.launch {
            getItemsByIdsUseCase(ids)
                .onSuccess { recentlyItemsDomain ->
                    updateState {
                        copy(
                            recentlyViewedItems = recentlyItemsDomain.toPresentation(),
                            isLoadingRecentlyViewed = false,
                            showNoConnectionError = false
                        )
                    }
                }
                .onFailure { result ->
                    if (result == DataError.Network.NO_CONNECTION) {
                        updateState {
                            copy(
                                isLoadingRecentlyViewed = false,
                                showNoConnectionError = true
                            )
                        }

                    } else {
                        updateState {
                            copy(
                                isLoadingRecentlyViewed = false,
                                showNoConnectionError = false
                            )
                        }
                    }
                }
        }
    }
}