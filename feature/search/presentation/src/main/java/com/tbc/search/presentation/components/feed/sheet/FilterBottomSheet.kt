package com.tbc.search.presentation.components.feed.sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.tbc.core.domain.model.category.Category
import com.tbc.core.presentation.mapper.category.toStringRes
import com.tbc.core_ui.components.button.PrimaryButton
import com.tbc.core_ui.components.checkbox.VoltechCheckBoxDefaults
import com.tbc.core_ui.components.textfield.OutlinedTextInputField
import com.tbc.core_ui.components.textfield.TextInputField
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.search.domain.model.feed.Condition
import com.tbc.search.domain.model.feed.Location
import com.tbc.resource.R
import com.tbc.search.presentation.mapper.feed.toStringRes
import com.tbc.search.presentation.screen.feed.FeedEvent
import com.tbc.search.presentation.screen.feed.FeedState

@Composable
fun FilterBottomSheet(
    state: FeedState,
    currentQuery: String,
    onEvent: (FeedEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(start = Dimen.size16),
            text = stringResource(R.string.filter),
            style = VoltechTextStyle.title1,
            color = VoltechColor.foregroundPrimary
        )
        Spacer(modifier = Modifier.height(Dimen.size16))

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = VoltechColor.borderStrong
        )

        LazyColumn(
            contentPadding = PaddingValues(vertical = Dimen.size16),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            // FASI
            item {
                Text(
                    modifier = Modifier.padding(start = Dimen.size16, bottom = Dimen.size16),
                    text = stringResource(R.string.price),
                    style = VoltechTextStyle.title2,
                    color = VoltechColor.foregroundPrimary
                )
            }

            item {
                PriceItem(
                    minPrice = state.filterState.minPrice,
                    maxPrice = state.filterState.maxPrice,
                    onMinPriceChanged = { onEvent(FeedEvent.UpdateMinPrice(it)) },
                    onMaxPriceChanged = { onEvent(FeedEvent.UpdateMaxPrice(it)) }
                )
                Spacer(modifier = Modifier.height(Dimen.size24))
            }

            // MDGOMAREOBA
            item {
                Text(
                    modifier = Modifier.padding(start = Dimen.size16, bottom = Dimen.size8),
                    text = stringResource(R.string.condition),
                    style = VoltechTextStyle.title2,
                    color = VoltechColor.foregroundPrimary
                )
            }
            items(Condition.entries) { condition ->
                FilterItem(
                    text = stringResource(condition.toStringRes()),
                    selected = state.filterState.selectedConditions.contains(condition),
                    onItemClick = { isChecked ->
                        onEvent(FeedEvent.ToggleCondition(condition, isChecked))
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(Dimen.size24)) }

            // LOKACIA
            item {
                Text(
                    modifier = Modifier.padding(start = Dimen.size16, bottom = Dimen.size8),
                    text = stringResource(R.string.location),
                    style = VoltechTextStyle.title2,
                    color = VoltechColor.foregroundPrimary
                )
            }
            items(Location.entries) { location ->
                FilterItem(
                    text = stringResource(location.toStringRes()),
                    selected = state.filterState.selectedLocations.contains(location),
                    onItemClick = { isChecked ->
                        onEvent(FeedEvent.ToggleLocation(location, isChecked))
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(Dimen.size24)) }

            // KATEGORIA
            item {
                Text(
                    modifier = Modifier.padding(start = Dimen.size16, bottom = Dimen.size8),
                    text = stringResource(R.string.category),
                    style = VoltechTextStyle.title2,
                    color = VoltechColor.foregroundPrimary
                )
            }
            items(Category.entries) { category ->
                FilterItem(
                    text = stringResource(category.toStringRes()),
                    selected = state.filterState.selectedCategories.contains(category),
                    onItemClick = { isChecked ->
                        onEvent(FeedEvent.ToggleCategory(category, isChecked))
                    }
                )
            }
            item { Spacer(modifier = Modifier.height(Dimen.size24)) }
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = VoltechColor.borderStrong
        )

        Spacer(modifier = Modifier.height(Dimen.size8))

        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = Dimen.size16)
                .fillMaxWidth(),
            text = stringResource(R.string.filter),
            onClick = {
                onEvent(FeedEvent.FilterItems(currentQuery))
            },
        )

        Spacer(modifier = Modifier.height(Dimen.size8))
    }
}

@Composable
private fun PriceItem(
    minPrice: String,
    maxPrice: String,
    onMinPriceChanged: (String) -> Unit,
    onMaxPriceChanged: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimen.size8),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextInputField(
            modifier = Modifier.weight(1f),
            value = minPrice,
            onTextChanged = { onMinPriceChanged(it) },
            label = stringResource(R.string.minimum_price),
            imeAction = ImeAction.Next,
            shape = VoltechRadius.radius12,
            keyboardType = KeyboardType.Number,
        )

        Spacer(modifier = Modifier.width(Dimen.size8))

        TextInputField(
            modifier = Modifier.weight(1f),
            value = maxPrice,
            onTextChanged = { onMaxPriceChanged(it) },
            label = stringResource(R.string.maximum_price),
            imeAction = ImeAction.Done,
            shape = VoltechRadius.radius12,
            keyboardType = KeyboardType.Number,
        )
    }
}

@Composable
private fun FilterItem(
    text: String,
    selected: Boolean,
    onItemClick: (Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(!selected) }
            .padding(vertical = Dimen.size12, horizontal = Dimen.size16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = selected,
            onCheckedChange = null,
            colors = VoltechCheckBoxDefaults.primaryColors

        )

        Spacer(modifier = Modifier.width(Dimen.size16))

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = text,
                style = VoltechTextStyle.body,
                color = VoltechColor.foregroundPrimary
            )
        }
    }
}