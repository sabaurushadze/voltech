package com.tbc.home.presentation.screen.home

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.recently_viewed.GetRecentlyUseCase
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
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
    private val getRecentlyUseCase: GetRecentlyUseCase,
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getItemsByIdsUseCase: GetItemsByIdsUseCase,
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()){

    override fun onEvent(event: HomeEvent) {
        when(event){
            HomeEvent.GetCategories -> getCategories()
            HomeEvent.GetRecentlyViewedItems -> getRecentlyIds()
        }
    }


    private fun getCategories(){
        viewModelScope.launch {
            getCategoriesUseCase()
                .onSuccess { categoryDomain ->
                    updateState { copy(categoryList = categoryDomain.toPresentation()) }
                }
        }
    }

    private fun getRecentlyIds(){
        val user = getCurrentUserUseCase()
        user?.let { user ->
            viewModelScope.launch {
                getRecentlyUseCase(user.uid)
                    .onSuccess { recentlyViewedDomain ->
                        updateState { copy(recentlyItemsId = recentlyViewedDomain.map { it.itemId }) }
                        getRecentlyViewedByIds(state.value.recentlyItemsId)
                    }
            }
        }
    }

    private fun getRecentlyViewedByIds(ids: List<Int>){
        viewModelScope.launch {
            getItemsByIdsUseCase(ids)
                .onSuccess { recentlyItemsDomain ->
                    updateState { copy(recentlyViewedItems = recentlyItemsDomain.toPresentation()) }
                }
        }
    }
}