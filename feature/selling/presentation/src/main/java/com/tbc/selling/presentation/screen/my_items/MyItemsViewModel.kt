package com.tbc.selling.presentation.screen.my_items

import android.util.Log.d
import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.DataError
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.user.toPresentation
import com.tbc.search.domain.usecase.feed.GetItemsByUidUseCase
import com.tbc.search.domain.usecase.feed.UpdateItemStatusUseCase
import com.tbc.selling.domain.usecase.selling.my_items.CheckUserItemAmountUseCase
import com.tbc.selling.presentation.mapper.my_items.toDomain
import com.tbc.selling.presentation.mapper.my_items.toPresentation
import com.tbc.selling.presentation.model.my_items.UiItemStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyItemsViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getItemsByUidUseCase: GetItemsByUidUseCase,
    private val checkUserItemAmountUseCase: CheckUserItemAmountUseCase,
    private val updateItemStatusUseCase: UpdateItemStatusUseCase,
) : BaseViewModel<MyItemsState, MyItemsSideEffect, MyItemsEvent>(MyItemsState()) {

    init {
        getCurrentUser()
    }

    override fun onEvent(event: MyItemsEvent) {
        when (event) {
            is MyItemsEvent.GetMyItems -> getMyItems()
            is MyItemsEvent.NavigateToItemDetails -> navigateToItemDetails(event.id)
            MyItemsEvent.NavigateToAddItem -> navigateToAddItem()
            MyItemsEvent.CanUserPostItems -> checkUserItemAmount()
            MyItemsEvent.DeleteFavoriteItemById -> deleteMyItemIds()
            MyItemsEvent.EditModeOff -> turnOffEditMode()
            MyItemsEvent.EditModeOn -> turnOnEditMode()
            is MyItemsEvent.ToggleSelectAll -> toggleSelectAll(event.selectAll)
            is MyItemsEvent.ToggleItemForDeletion -> toggleItemSelection(event.id)

        }
    }

    fun toggleItemSelection(currentId: Int) {
        updateState {
            copy(
                myItems = myItems.map { item ->
                    if (item.id == currentId) {
                        item.copy(isSelected = !item.isSelected)
                    } else item
                }
            )
        }
    }

    private fun deleteMyItemIds() = viewModelScope.launch {
        val mySelectedItemIds = state.value.myItems
            .filter { it.isSelected }
            .map { it.id }

        mySelectedItemIds.forEach { id ->
            updateItemStatusUseCase(id = id, itemStatus = UiItemStatus(active = false).toDomain())
                .onSuccess { d("asdd", "SCAHUSDHAUSD$it") }
                .onFailure { d("asdd", "FAAAILL$it") }
        }

        getMyItems()
    }

    private fun toggleSelectAll(selectAll: Boolean) {
        updateState {
            copy(
                myItems = myItems.map { it.copy(isSelected = selectAll) }
            )
        }
    }

    private fun turnOnEditMode() {
        updateState { copy(editModeOn = true) }
    }

    private fun turnOffEditMode() {
        updateState {
            copy(
                editModeOn = false,
                myItems = myItems.map { it.copy(isSelected = false) }
            )
        }
    }

    private fun navigateToItemDetails(id: Int) {
        emitSideEffect(MyItemsSideEffect.NavigateToItemDetails(id))
    }

    private fun checkUserItemAmount() = viewModelScope.launch {
        state.value.user?.let { user ->
            getItemsByUidUseCase(user.uid)
                .onSuccess { items ->
                    val canUserAddItem = checkUserItemAmountUseCase(items.size)
                    updateState { copy(userCanAddItem = canUserAddItem) }
                }
        }
    }

    private fun navigateToAddItem() {
        emitSideEffect(MyItemsSideEffect.NavigateToAddItem)
    }

    private fun getMyItems() = viewModelScope.launch {
        updateState { copy(isLoading = true) }

        state.value.user?.let { user ->
            getItemsByUidUseCase(user.uid)
                .onSuccess { itemsDomain ->
                    updateState {
                        copy(
                            myItems = itemsDomain.map { it.toPresentation() },
                            isLoading = false,
                            showNoConnectionError = false
                        )
                    }
                }
                .onFailure { result ->
                    if (result == DataError.Network.NO_CONNECTION) {
                        updateState {
                            copy(
                                isLoading = false,
                                showNoConnectionError = true
                            )
                        }

                    } else {
                        updateState { copy(isLoading = false, showNoConnectionError = false) }
                    }
                }
        }
    }

    private fun getCurrentUser() = viewModelScope.launch {
        val currentUser = getCurrentUserUseCase()?.toPresentation()
        updateState { copy(user = currentUser) }
    }

}