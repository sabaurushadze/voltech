package com.tbc.selling.presentation.screen.add_item

import androidx.lifecycle.viewModelScope
import com.tbc.core.domain.model.category.Category
import com.tbc.core.domain.usecase.user.GetCurrentUserUseCase
import com.tbc.core.domain.util.onFailure
import com.tbc.core.domain.util.onSuccess
import com.tbc.core.presentation.base.BaseViewModel
import com.tbc.core.presentation.mapper.user.toPresentation
import com.tbc.profile.domain.usecase.edit_profile.EnqueueMultipleFileUploadUseCase
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.Location
import com.tbc.search.domain.usecase.feed.AddItemUseCase
import com.tbc.selling.domain.usecase.selling.add_item.ValidateDescriptionUseCase
import com.tbc.selling.domain.usecase.selling.add_item.ValidatePriceUseCase
import com.tbc.selling.domain.usecase.selling.add_item.ValidateQuantityUseCase
import com.tbc.selling.domain.usecase.selling.add_item.ValidateTitleUseCase
import com.tbc.selling.presentation.mapper.my_items.toDomain
import com.tbc.selling.presentation.model.add_item.UiItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddItemViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val enqueueMultipleFileUploadUseCase: EnqueueMultipleFileUploadUseCase,
    private val validateTitleUseCase: ValidateTitleUseCase,
    private val validateDescriptionUseCase: ValidateDescriptionUseCase,
    private val validateQuantityUseCase: ValidateQuantityUseCase,
    private val validatePriceUseCase: ValidatePriceUseCase,
    private val addItemUseCase: AddItemUseCase,
) : BaseViewModel<AddItemState, AddItemSideEffect, AddItemEvent>(AddItemState()) {

    init {
        getCurrentUser()
    }

    override fun onEvent(event: AddItemEvent) {
        when (event) {
            AddItemEvent.NavigateBackToMyItems -> navigateBackToMyItems()
            is AddItemEvent.SelectCategory -> selectCategory(event.category)
            is AddItemEvent.SelectCondition -> selectCondition(event.condition)
            is AddItemEvent.SelectLocation -> selectLocation(event.location)

            is AddItemEvent.OnImagesSelected -> updateState {
                copy(selectedImageUris = selectedImageUris + event.uris)
            }

            AddItemEvent.LaunchGallery -> emitSideEffect(AddItemSideEffect.LaunchGallery)
            AddItemEvent.ResetImageError -> updateState { copy(showImageError = false) }
            is AddItemEvent.DeleteImageFromPreview -> updateState {
                copy(
                    selectedImageUris = selectedImageUris.filterNot { it == event.uri }
                )
            }

            is AddItemEvent.TitleChanged -> updateTitle(event.title)
            is AddItemEvent.DescriptionChanged -> updateDescription(event.description)
            is AddItemEvent.PriceChanged -> updatePrice(event.price)
            is AddItemEvent.QuantityChanged -> updateQuantity(event.quantity)
            AddItemEvent.AddItem -> addItem()
            AddItemEvent.ClearDescription -> updateState { copy(description = "") }
        }
    }

    private fun addItem() = with(state.value) {
        viewModelScope.launch {

            updateState {
                copy(
                    isLoading = true,
                    showTitleError = false,
                    showDescriptionError = false,
                    showPriceError = false,
                    showCategoryError = false,
                    showConditionError = false,
                    showLocationError = false,
                    showQuantityError = false,
                    showImageError = false,
                )
            }

            val isTitleValid = validateTitleUseCase(title)
            val isDescriptionValid = validateDescriptionUseCase(description)
            val isQuantityValid = validateQuantityUseCase(quantity)
            val isPriceValid = validatePriceUseCase(price)
            val isImageUploaded = selectedImageUris.isNotEmpty()

            val currentUser = user
            val category = selectedCategory
            val condition = selectedCondition
            val location = selectedLocation

            val isFormValid =
                isTitleValid &&
                isDescriptionValid &&
                isQuantityValid &&
                isPriceValid &&
                isImageUploaded &&
                currentUser != null &&
                category != null &&
                condition != null &&
                location != null

            if (!isFormValid) {
                updateState {
                    copy(
                        showTitleError = !isTitleValid,
                        showDescriptionError = !isDescriptionValid,
                        showCategoryError = selectedCategory == null,
                        showImageError = !isImageUploaded,
                        showConditionError = selectedCondition == null,
                        showLocationError = selectedLocation == null,
                        showQuantityError = !isQuantityValid,
                        showPriceError = !isPriceValid,
                        isLoading = false
                    )
                }
                return@launch
            }

            enqueueMultipleFileUploadUseCase(selectedImageUris.map { it.toString() })
                .onSuccess { urls ->

                    val item = UiItem(
                        uid = currentUser.uid,
                        title = title,
                        category = category,
                        condition = condition,
                        price = price,
                        images = urls,
                        quantity = quantity,
                        location = location,
                        userDescription = description,
                        sellerName = currentUser.name,
                        sellerPhotoUrl = currentUser.photoUrl
                    ).toDomain()

                    addItemUseCase(item)
                        .onSuccess {
                            updateState { copy(isLoading = false,) }
                            emitSideEffect(AddItemSideEffect.NavigateBackToMyItems)
                        }
                        .onFailure {
                            updateState { copy(isLoading = false) }
                        }
                }
                .onFailure {
                    updateState { copy(isLoading = false) }
                }
        }
    }

    private fun updateTitle(title: String) {
        updateState { copy(title = title) }
    }

    private fun updateDescription(description: String) {
        updateState { copy(description = description) }
    }

    private fun updatePrice(price: String) {
        updateState { copy(price = price) }
    }

    private fun updateQuantity(quantity: String) {
        updateState { copy(quantity = quantity) }
    }

    private fun selectCategory(category: Category) {
        updateState { copy(selectedCategory = category) }
    }

    private fun selectCondition(condition: Condition) {
        updateState { copy(selectedCondition = condition) }
    }

    private fun selectLocation(location: Location) {
        updateState { copy(selectedLocation = location) }
    }

    private fun navigateBackToMyItems() {
        emitSideEffect(AddItemSideEffect.NavigateBackToMyItems)
    }

    private fun getCurrentUser() = viewModelScope.launch {
        val currentUser = getCurrentUserUseCase()?.toPresentation()
        updateState { copy(user = currentUser) }
    }


}