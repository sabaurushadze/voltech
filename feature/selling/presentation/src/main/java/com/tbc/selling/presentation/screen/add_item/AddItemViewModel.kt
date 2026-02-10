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
import com.tbc.search.domain.usecase.feed.GetItemsByUidUseCase
import com.tbc.selling.domain.usecase.selling.ValidateDescriptionUseCase
import com.tbc.selling.domain.usecase.selling.ValidatePriceUseCase
import com.tbc.selling.domain.usecase.selling.ValidateQuantityUseCase
import com.tbc.selling.domain.usecase.selling.ValidateTitleUseCase
import com.tbc.selling.presentation.mapper.my_items.toDomain
import com.tbc.selling.presentation.mapper.my_items.toPresentation
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

            AddItemEvent.UploadImages -> uploadImages()
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

    private fun addItem() = viewModelScope.launch {
        updateState {
            copy(
                isLoading = true,
                showTitleError = false,
                showDescriptionError = false,
                showCategoryError = false
            )
        }

        val isTitleValid = validateTitleUseCase(state.value.title)
        val isDescriptionValid = validateDescriptionUseCase(state.value.description)
        val isCategorySelected = state.value.selectedCategory != null
        val isConditionSelected = state.value.selectedCondition != null
        val isLocationSelected = state.value.selectedLocation != null
        val isQuantityValid = validateQuantityUseCase(state.value.quantity)
        val isPriceValid = validatePriceUseCase(state.value.price)
        val isImageUploaded = state.value.selectedImageUris.isNotEmpty()

        if (isTitleValid && isDescriptionValid && isCategorySelected && isImageUploaded
            && isConditionSelected && isLocationSelected && isQuantityValid && isPriceValid
        ) {

        val quantity = state.value.quantity.toIntOrNull()
        val price = state.value.price.toDoubleOrNull()

        enqueueMultipleFileUploadUseCase(state.value.selectedImageUris.map { it.toString() })
            .onSuccess { urls ->
                updateState { copy(uploadedUrls = urls) }

                with(state.value) {
                    if (
                        user != null && selectedLocation != null && selectedCondition != null &&
                        quantity != null && selectedCategory != null && price != null
                    ) {
                        val item = UiItem(
                            uid = user.uid,
                            title = title,
                            category = selectedCategory,
                            condition = selectedCondition,
                            price = price,
                            images = uploadedUrls,
                            quantity = quantity,
                            location = selectedLocation,
                            userDescription = description,
                            sellerAvatar = user.photoUrl,
                            sellerUserName = user.name
                        ).toDomain()

                        addItemUseCase(item)
                    }

                }
            }
        } else {
            updateState {
                copy(
                    showTitleError = !isTitleValid,
                    showDescriptionError = !isDescriptionValid,
                    showCategoryError = !isCategorySelected,
                    showImageError = !isImageUploaded,
                    showConditionError = !isConditionSelected,
                    showLocationError = !isLocationSelected,
                    showQuantityError = !isQuantityValid,
                    showPriceError = !isPriceValid,
                    isLoading = false
                )
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

    private fun uploadImages() = viewModelScope.launch {
        val uris = state.value.selectedImageUris
        if (uris.isEmpty()) return@launch

        updateState { copy(isUploading = true) }

        enqueueMultipleFileUploadUseCase(uris.map { it.toString() })
            .onSuccess { result ->
                updateState { copy(uploadedUrls = result, isUploading = false) }
            }
            .onFailure { updateState { copy(isUploading = false) } }
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