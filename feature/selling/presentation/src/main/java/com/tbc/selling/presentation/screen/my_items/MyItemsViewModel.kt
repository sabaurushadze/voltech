package com.tbc.selling.presentation.screen.my_items

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.user.toPresentation
import com.tbc.search.domain.usecase.feed.DeleteItemByIdUseCase
import com.tbc.search.domain.usecase.feed.GetItemsByUidUseCase
import com.tbc.selling.domain.usecase.selling.my_items.CheckUserItemAmountUseCase
import com.tbc.selling.presentation.mapper.my_items.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyItemsViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val getItemsByUidUseCase: GetItemsByUidUseCase,
    private val checkUserItemAmountUseCase: CheckUserItemAmountUseCase,
    private val deleteItemByIdUseCase: DeleteItemByIdUseCase
) : BaseViewModel<MyItemsState, MyItemsSideEffect, MyItemsEvent>(MyItemsState()) {

    init {
        getCurrentUser()
    }
    override fun onEvent(event: MyItemsEvent) {
        when (event) {
            is MyItemsEvent.GetMyItems -> getMyItems(event.uid)
            is MyItemsEvent.NavigateToItemDetails -> navigateToItemDetails(event.id)
            MyItemsEvent.NavigateToAddItem -> navigateToAddItem()
            MyItemsEvent.CanUserPostItems -> checkUserItemAmount()
            MyItemsEvent.DeleteFavoriteItemById -> deleteFavoriteItemIds()
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
    private fun deleteFavoriteItemIds() = viewModelScope.launch {
        val selectedFavoriteIds = state.value.myItems
            .filter { it.isSelected }
            .map { it.id }

        selectedFavoriteIds.forEach { id ->
            deleteItemByIdUseCase(id)
        }

        state.value.user?.let { user ->
            getMyItems(user.uid)
        }
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

    private fun getMyItems(uid: String) = viewModelScope.launch {
        getItemsByUidUseCase(uid)
            .onSuccess { itemsDomain ->
                updateState { copy(myItems = itemsDomain.map { it.toPresentation() }, isLoading = false) }
            }
    }

    private fun getCurrentUser() = viewModelScope.launch {
        val currentUser = getCurrentUserUseCase()?.toPresentation()
        updateState { copy(user = currentUser) }
    }

}