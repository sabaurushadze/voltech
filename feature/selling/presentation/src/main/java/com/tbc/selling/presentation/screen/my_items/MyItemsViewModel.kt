package com.tbc.selling.presentation.screen.my_items

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.user.toPresentation
import com.tbc.search.domain.usecase.feed.GetItemsByUidUseCase
import com.tbc.selling.presentation.mapper.my_items.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyItemsViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getItemsByUidUseCase: GetItemsByUidUseCase
) : BaseViewModel<MyItemsState, MyItemsSideEffect, MyItemsEvent>(MyItemsState()) {

    init {
        getCurrentUser()
    }
    override fun onEvent(event: MyItemsEvent) {
        when (event) {
            is MyItemsEvent.GetMyItems -> getMyItems(event.uid)
            MyItemsEvent.NavigateToAddItem -> navigateToAddItem()
        }
    }

    private fun navigateToAddItem() {
        emitSideEffect(MyItemsSideEffect.NavigateToAddItem)
    }

    private fun getMyItems(uid: String) = viewModelScope.launch {
        getItemsByUidUseCase(uid)
            .onSuccess { itemsDomain ->
                updateState { copy(myItems = itemsDomain.map { it.toPresentation() }) }
            }
    }

    private fun getCurrentUser() = viewModelScope.launch {
        val currentUser = getCurrentUserUseCase()?.toPresentation()
        updateState { copy(user = currentUser) }
    }

}