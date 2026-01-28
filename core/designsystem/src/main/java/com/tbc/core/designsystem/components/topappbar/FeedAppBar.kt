package com.tbc.core.designsystem.components.topappbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.tbc.core.designsystem.R
import com.tbc.core.designsystem.components.button.IconTextButton
import com.tbc.core.designsystem.components.textfield.TextInputFieldDummy
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechRadius
import com.tbc.core.designsystem.theme.VoltechTextStyle


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedAppBar(
    onSearchClick: () -> Unit,
    onSortClick: () -> Unit,
    onFilterClick: () -> Unit,
    isLoading: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    isContentReady: Boolean,
) {
    TopAppBar(
        title = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextInputFieldDummy(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = Dimen.size16),
                    label = stringResource(R.string.search_on_voltech),
                    shape = VoltechRadius.radius24,
                    startIcon = ImageVector.vectorResource(R.drawable.ic_search),
                    onClick = onSearchClick,
                )

                Spacer(modifier = Modifier.height(Dimen.size8))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(VoltechColor.background)
                        .padding(end = Dimen.size16),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!isContentReady) {
                        Text(
                            text = stringResource(R.string.loading_searching),
                            style = VoltechTextStyle.body16Bold,
                            color = VoltechColor.onBackground,
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    IconTextButton(
                        icon = ImageVector.vectorResource(R.drawable.ic_filter),
                        textRes = R.string.sort,
                        loading = isLoading,
                        enabled = !isLoading,
                        onClick = onSortClick,
                    )
                    IconTextButton(
                        icon = ImageVector.vectorResource(R.drawable.ic_sort),
                        textRes = R.string.filter,
                        loading = isLoading,
                        enabled = !isLoading,
                        onClick = onFilterClick,
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        colors = VoltechTopAppBarDefaults.primaryColors,
        modifier = modifier
    )
}