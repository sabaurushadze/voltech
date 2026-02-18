package com.tbc.search.presentation.components.feed.sheet

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.tbc.core_ui.components.button.PrimaryButton
import com.tbc.core_ui.components.radiobutton.VoltechRadioButtonDefaults
import com.tbc.core_ui.components.textfield.OutlinedTextInputField
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechFixedColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R
import com.tbc.search.presentation.enums.item_details.Rating
import com.tbc.search.presentation.mapper.item_details.toStringRes
import com.tbc.search.presentation.screen.item_details.ItemDetailsEvent
import com.tbc.search.presentation.screen.item_details.ItemDetailsState

@Composable
fun ReviewBottomSheet(
    options: List<Rating>,
    selectedSortType: Rating,
    state: ItemDetailsState,
    onEvent: (ItemDetailsEvent) -> Unit,
) {
    val descriptionError =
        if (state.showDescriptionError) stringResource(R.string.enter_valid_description) else null

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(start = Dimen.size16),
            text = stringResource(R.string.leave_a_review),
            style = VoltechTextStyle.title1,
            color = VoltechColor.foregroundPrimary
        )

        Spacer(modifier = Modifier.height(Dimen.size16))

        options.forEach { option ->
            RatingItem(
                titleRes = option.toStringRes(),
                selected = option == selectedSortType,
                onItemClick = { onEvent(ItemDetailsEvent.SelectRating(option)) }
            )
        }

        Spacer(modifier = Modifier.height(Dimen.size16))

        Column (
            modifier = Modifier.padding(horizontal = Dimen.size16)
        ){
            OutlinedTextInputField(
                modifier = Modifier
                    .heightIn(Dimen.size100, Dimen.size350)
                    .fillMaxWidth(),
                value = state.description,
                singleLine = false,
                maxLines = 10,
                onTextChanged = { onEvent(ItemDetailsEvent.DescriptionChanged(it)) },
                label = stringResource(R.string.item_description),
                shape = VoltechRadius.radius8,
                errorText = descriptionError,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
                endIcon = {
                    if (state.description.isNotEmpty()) {
                        DeleteCircleButton(
                            onIconClick = { onEvent(ItemDetailsEvent.ClearDescription) },
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

        Spacer(modifier = Modifier.height(Dimen.size16))

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.size16),
            text = stringResource(R.string.submit_review),
            onClick = { onEvent(ItemDetailsEvent.SubmitReview) }
        )

        Spacer(modifier = Modifier.height(Dimen.size16))

    }
}

@Composable
fun RatingItem(
    @StringRes titleRes: Int,
    selected: Boolean,
    onItemClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onItemClick
            )
            .padding(vertical = Dimen.size12, horizontal = Dimen.size16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
            colors = VoltechRadioButtonDefaults.primaryColors
        )

        Spacer(modifier = Modifier.width(Dimen.size16))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(titleRes),
                style = VoltechTextStyle.body,
                color = VoltechColor.foregroundPrimary
            )
        }
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