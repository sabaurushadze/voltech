package com.tbc.search.presentation.components.feed.topbar

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
import com.tbc.core_ui.components.button.BorderlessIconButton
import com.tbc.core_ui.components.textfield.TextInputFieldDummy
import com.tbc.core_ui.components.topbar.VoltechTopAppBarDefaults
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R


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
    searchQuery: String = ""
    isRefreshing: Boolean,
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
                    value = searchQuery,
                    shape = VoltechRadius.radius24,
                    startIcon = ImageVector.vectorResource(R.drawable.ic_search),
                    onClick = onSearchClick,
                )

                Spacer(modifier = Modifier.height(Dimen.size8))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(VoltechColor.backgroundPrimary)
                        .padding(end = Dimen.size16),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isRefreshing) {
                        Text(
                            text = stringResource(R.string.loading_searching),
                            style = VoltechTextStyle.bodyBold,
                            color = VoltechColor.foregroundPrimary,
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    BorderlessIconButton(
                        icon = R.drawable.ic_filter,
                        text = stringResource(R.string.sort),
                        enabled = !isLoading,
                        onClick = onSortClick,
                    )
                    BorderlessIconButton(
                        icon = R.drawable.ic_sort,
                        text = stringResource(R.string.filter),
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