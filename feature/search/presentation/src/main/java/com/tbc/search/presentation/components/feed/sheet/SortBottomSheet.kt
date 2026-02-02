package com.tbc.search.presentation.components.feed.sheet

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tbc.core_ui.components.radiobutton.VoltechRadioButtonDefaults
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.core_ui.theme.VoltechTheme
import com.tbc.resource.R
import com.tbc.search.presentation.enums.feed.SortType
import com.tbc.search.presentation.mapper.feed.toStringRes

@Composable
fun SortBottomSheet(
    options: List<SortType>,
    selectedSortType: SortType,
    onItemClick: (SortType) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(start = Dimen.size16),
            text = "Sort",
            style = VoltechTextStyle.body22Bold,
            color = VoltechColor.foregroundPrimary
        )

        Spacer(modifier = Modifier.height(Dimen.size16))

        options.forEach { option ->
            SortItem(
                titleRes = option.toStringRes(),
                selected = option == selectedSortType,
                onItemClick = { onItemClick(option) }
            )
        }
    }
}

@Composable
fun SortItem(
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
                style = VoltechTextStyle.body16Normal,
                color = VoltechColor.foregroundPrimary
            )
        }
    }
}


@Composable
@Preview
fun SortBottomSheetPreview() {
    VoltechTheme {
        SortItem(
            titleRes = R.string.sort_price_lowest,
            true
        ) {}
    }
}