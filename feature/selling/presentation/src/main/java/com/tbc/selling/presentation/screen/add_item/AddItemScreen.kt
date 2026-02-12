package com.tbc.selling.presentation.screen.add_item

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.domain.model.category.Category
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.extension.collectSideEffect
import com.tbc.core.presentation.mapper.category.toStringRes
import com.tbc.core.presentation.util.rememberMultiGalleryLauncher
import com.tbc.core_ui.components.button.PrimaryButton
import com.tbc.core_ui.components.button.TertiaryIconButton
import com.tbc.core_ui.components.dropdown.DropDownMenu
import com.tbc.core_ui.components.image.BaseAsyncImage
import com.tbc.core_ui.components.loading.LoadingIcon
import com.tbc.core_ui.components.textfield.OutlinedTextInputField
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechFixedColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.Location
import com.tbc.selling.presentation.mapper.my_items.toStringRes

@Composable
fun AddItemScreen(
    viewModel: AddItemViewModel = hiltViewModel(),
    navigateBackToMyItems: () -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val launchGallery = rememberMultiGalleryLauncher(
        onImagesSelected = { selectedUris ->
            viewModel.onEvent(AddItemEvent.OnImagesSelected(selectedUris))
        }
    )

    viewModel.sideEffect.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is AddItemSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }

            AddItemSideEffect.NavigateBackToMyItems -> {
                navigateBackToMyItems()
            }

            AddItemSideEffect.LaunchGallery -> launchGallery()
        }
    }



    AddItemContent(
        state = state,
        onEvent = viewModel::onEvent,
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddItemContent(
    state: AddItemState,
    onEvent: (AddItemEvent) -> Unit,
) {
    val titleError = if (state.showTitleError) stringResource(R.string.enter_valid_title) else null
    val descriptionError =
        if (state.showDescriptionError) stringResource(R.string.enter_valid_description) else null
    val priceError = if (state.showPriceError) stringResource(R.string.enter_valid_price) else null
    val categoryError =
        if (state.showCategoryError) stringResource(R.string.select_a_category) else ""
    val conditionError =
        if (state.showConditionError) stringResource(R.string.select_condition) else ""
    val locationError =
        if (state.showLocationError) stringResource(R.string.select_location) else ""
    val quantityError =
        if (state.showQuantityError) stringResource(R.string.select_valid_quantity_amount) else null
    val imageError =
        if (state.showImageError) stringResource(R.string.please_select_at_least_one_image) else ""

    LazyColumn(
        modifier = Modifier
            .navigationBarsPadding()
            .imePadding()
            .padding(horizontal = Dimen.size16)
    ) {
        item { Spacer(modifier = Modifier.height(Dimen.size16)) }

        item {
            Column {
                OutlinedTextInputField(
                    modifier = Modifier
                        .heightIn(Dimen.size64, Dimen.size150)
                        .fillMaxWidth(),
                    value = state.title,
                    singleLine = false,
                    maxLines = 3,
                    onTextChanged = { onEvent(AddItemEvent.TitleChanged(it)) },
                    label = stringResource(R.string.title),
                    errorText = titleError,
                    shape = VoltechRadius.radius8,
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                )
                LiveTextSizeViewer(
                    maxAmount = stringResource(R.string.max_title_length_eighty),
                    currentAmount = state.title.length.toString()
                )
            }

        }

        item { Spacer(modifier = Modifier.height(Dimen.size16)) }

        item {
            Column {
                OutlinedTextInputField(
                    modifier = Modifier
                        .heightIn(Dimen.size100, Dimen.size350)
                        .fillMaxWidth(),
                    value = state.description,
                    singleLine = false,
                    maxLines = 10,
                    onTextChanged = { onEvent(AddItemEvent.DescriptionChanged(it)) },
                    label = stringResource(R.string.item_description),
                    shape = VoltechRadius.radius8,
                    errorText = descriptionError,
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text,
                    endIcon = {
                        if (state.description.isNotEmpty()) {
                            DeleteCircleButton(
                                onIconClick = { onEvent(AddItemEvent.ClearDescription) },
                                backgroundColor = VoltechColor.backgroundInverse,
                                iconColor = VoltechColor.foregroundOnInverse,
                            )
                        }
                    }
                )
                LiveTextSizeViewer(
                    maxAmount = stringResource(R.string.max_description_length),
                    currentAmount = state.description.length.toString()
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(Dimen.size16))
        }

        item {
            OutlinedTextInputField(
                modifier = Modifier
                    .heightIn(Dimen.size64, Dimen.size150)
                    .fillMaxWidth(),
                value = state.price,
                onTextChanged = { onEvent(AddItemEvent.PriceChanged(it)) },
                label = stringResource(R.string.price),
                errorText = priceError,
                shape = VoltechRadius.radius8,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
            )
        }

        item {
            Spacer(modifier = Modifier.height(Dimen.size16))
        }

        item {
            DropDownMenu(
                label = stringResource(R.string.category),
                shape = VoltechRadius.radius8,
                items = Category.entries,
                selectedItem = state.selectedCategory,
                errorText = categoryError,
                onItemSelected = { category ->
                    onEvent(AddItemEvent.SelectCategory(category))
                },
                itemText = { category ->
                    stringResource(category.toStringRes())
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(Dimen.size8))
        }

        item {
            DropDownMenu(
                label = stringResource(R.string.condition),
                shape = VoltechRadius.radius8,
                items = Condition.entries,
                selectedItem = state.selectedCondition,
                errorText = conditionError,
                onItemSelected = { condition ->
                    onEvent(AddItemEvent.SelectCondition(condition))
                },
                itemText = { category ->
                    stringResource(category.toStringRes())
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(Dimen.size8))
        }

        item {
            DropDownMenu(
                label = stringResource(R.string.location),
                shape = VoltechRadius.radius8,
                items = Location.entries,
                selectedItem = state.selectedLocation,
                errorText = locationError,
                onItemSelected = { location ->
                    onEvent(AddItemEvent.SelectLocation(location))
                },
                itemText = { category ->
                    stringResource(category.toStringRes())
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(Dimen.size8))
        }

        item {
            OutlinedTextInputField(
                modifier = Modifier
                    .heightIn(Dimen.size64, Dimen.size150)
                    .fillMaxWidth(),
                value = state.quantity,
                onTextChanged = { onEvent(AddItemEvent.QuantityChanged(it)) },
                label = stringResource(R.string.quantity),
                errorText = quantityError,
                shape = VoltechRadius.radius8,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
            )
        }

        item {
            Spacer(modifier = Modifier.height(Dimen.size8))
        }

        item {
            ImagesGrid(
                uris = state.selectedImageUris,
                onAddImagesClick = {
                    onEvent(AddItemEvent.LaunchGallery)
                    onEvent(AddItemEvent.ResetImageError)
                },
                deleteImage = { uri ->
                    onEvent(AddItemEvent.DeleteImageFromPreview(uri))
                },
                errorText = imageError
            )
        }

        item {
            if (state.isLoading) {
                LoadingIcon()
            }
            Spacer(modifier = Modifier.height(Dimen.size16))
        }


        item {
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = !state.isLoading,
                text = stringResource(R.string.add_item),
                onClick = {
                    onEvent(AddItemEvent.AddItem)
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(Dimen.size16))
        }
    }
}

@Composable
fun ImagesGrid(
    uris: List<Uri>,
    onAddImagesClick: () -> Unit,
    deleteImage: (Uri) -> Unit,
    errorText: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimen.size16)
            .height(Dimen.size216)
    ) {
        if (uris.isEmpty()) {
            AddImagesPlaceholder(
                onClick = onAddImagesClick,
                errorText = errorText
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(Dimen.size8),
                verticalArrangement = Arrangement.spacedBy(Dimen.size8),
                modifier = Modifier.fillMaxSize()
            ) {
                items(uris) { uri ->
                    ImagePreviewWithDelete(
                        uri = uri,
                        onDeleteClick = { deleteImage(uri) }
                    )
                }
            }
        }
    }
}

@Composable
private fun AddImagesPlaceholder(
    onClick: () -> Unit,
    errorText: String,
) {
    TertiaryIconButton(
        text = stringResource(R.string.add_images),
        iconSize = Dimen.size32,
        borderColor = if (errorText.isNotEmpty()) VoltechColor.foregroundAttention else VoltechColor.foregroundSecondary,
        errorText = errorText,
        onClick = onClick,
        icon = R.drawable.ic_camera,
        modifier = Modifier
            .height(Dimen.size186)
            .fillMaxWidth()
    )
}

@Composable
private fun ImagePreviewWithDelete(
    uri: Uri,
    onDeleteClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clip(VoltechRadius.radius16)
            .size(Dimen.size100)
    ) {
        BaseAsyncImage(
            url = uri.toString(),
            modifier = Modifier
                .size(Dimen.size100)
        )
        DeleteCircleButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onIconClick = { onDeleteClick() },
        )
    }
}

@Composable
private fun DeleteCircleButton(
    modifier: Modifier = Modifier,
    onIconClick: () -> Unit,
    backgroundColor: Color = VoltechFixedColor.black.copy(alpha = 0.7f),
    iconColor: Color = VoltechFixedColor.white,
    @DrawableRes icon: Int = R.drawable.ic_remove_x,
) {
    Box(
        modifier = modifier
            .padding(Dimen.size6)
            .size(Dimen.size24)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onIconClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(Dimen.size8),
            imageVector = ImageVector.vectorResource(icon),
            tint = iconColor,
            contentDescription = null
        )
    }
}

@Composable
private fun LiveTextSizeViewer(
    maxAmount: String,
    currentAmount: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = currentAmount,
            color = VoltechColor.foregroundSecondary,
            style = VoltechTextStyle.caption
        )
        Text(
            text = stringResource(R.string.slash),
            color = VoltechColor.foregroundSecondary,
            style = VoltechTextStyle.caption
        )
        Text(
            text = maxAmount,
            color = VoltechColor.foregroundSecondary,
            style = VoltechTextStyle.caption
        )
    }
}
