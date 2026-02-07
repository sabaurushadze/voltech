package com.tbc.home.presentation.screen

import android.util.Log.d
import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.home.domain.usecase.GetCategoriesUseCase
import com.tbc.home.presentation.screen.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : BaseViewModel<HomeState, HomeSideEffect, HomeEvent>(HomeState()){

    override fun onEvent(event: HomeEvent) {
        when(event){
            HomeEvent.GetCategories -> getCategories()
        }
    }

    private fun getCategories(){
        viewModelScope.launch {
            getCategoriesUseCase()
                .onSuccess { categoryDomain ->
                    updateState { copy(categoryList = categoryDomain.toPresentation()) }
                    d("success", "view ${categoryDomain}")
                }
                .onFailure {
                    d("success", "erro $it")
                }
        }
    }
}