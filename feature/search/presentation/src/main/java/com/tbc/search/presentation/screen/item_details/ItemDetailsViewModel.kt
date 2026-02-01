package com.tbc.search.presentation.screen.item_details

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.toStringResId
import com.tbc.search.domain.usecase.feed.GetItemDetailsUseCase
import com.tbc.search.presentation.mapper.feed.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailsViewModel @Inject constructor(
    private val getItemDetailsUseCase: GetItemDetailsUseCase

) : BaseViewModel<ItemDetailsState, ItemDetailsSideEffect, ItemDetailsEvent>(ItemDetailsState()) {

    override fun onEvent(event: ItemDetailsEvent) {
        when (event) {
            is ItemDetailsEvent.GetItemDetails -> getItemDetails(event.id)
            is ItemDetailsEvent.SelectImageByIndex -> updateState { copy(selectedImage = event.index) }
            ItemDetailsEvent.NavigateBackToFeed -> navigateBackToFeed()
        }
    }

    private fun navigateBackToFeed() {
        emitSideEffect(ItemDetailsSideEffect.NavigateBackToFeed)
    }

    private fun getItemDetails(id: Int) = viewModelScope.launch {
        updateState { copy(isLoading = true) }

        getItemDetailsUseCase(id)
            .onSuccess { itemDetailsDomain ->
                updateState { copy(itemDetails = itemDetailsDomain.toPresentation()) }
                updateState { copy(isLoading = false) }
            }
            .onFailure {
                emitSideEffect(ItemDetailsSideEffect.ShowSnackBar(errorRes = it.toStringResId()))
                updateState { copy(isLoading = false) }
            }
    }
}