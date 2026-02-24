package com.tbc.search.presentation.components.feed.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
    isLoading: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    searchQuery: String = "",
    isRefreshing: Boolean,
    onSearchClick: () -> Unit,
    onSortClick: () -> Unit,
    onFilterClick: () -> Unit,
    navigateBack: () -> Unit,
) {
    TopAppBar(
        title = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .clickable { navigateBack() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(Dimen.size32)
                                .padding(top = Dimen.size8),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                            contentDescription = null,
                            tint = VoltechColor.foregroundPrimary
                        )
                    }

                    Spacer(modifier = Modifier.width(Dimen.size16))

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
                }

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
                        textStyle = VoltechTextStyle.title3,
                        onClick = onSortClick,
                    )
                    BorderlessIconButton(
                        icon = R.drawable.ic_sort,
                        text = stringResource(R.string.filter),
                        enabled = !isLoading,
                        textStyle = VoltechTextStyle.title3,
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