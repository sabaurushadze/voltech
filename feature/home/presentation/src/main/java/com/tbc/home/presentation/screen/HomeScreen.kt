package com.tbc.home.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.presentation.base.BaseAsyncImage
import com.tbc.core.presentation.extension.capitalizeFirst
import com.tbc.core.presentation.mapper.category.toStringRes
import com.tbc.core_ui.components.button.CircleIconButton
import com.tbc.core_ui.components.textfield.TextInputFieldDummy
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechFixedColor
import com.tbc.core_ui.theme.VoltechRadius
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.home.presentation.screen.model.UiCategoryItem
import com.tbc.resource.R

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onSetupTopBar: (TopBarState) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToFeed: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    SetupTopBar(onSetupTopBar, navigateToSearch)

    LaunchedEffect(Unit) {
        onEvent(HomeEvent.GetCategories)
    }

    HomeContent(state, navigateToFeed)
}

@Composable
private fun HomeContent(
    state: HomeState,
    navigateToFeed: (String) -> Unit,
){

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
            .padding(top = Dimen.size24)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.categories),
                    style = VoltechTextStyle.title1,
                    color = VoltechColor.foregroundPrimary
                )

                Spacer(Modifier.height(Dimen.size32))

                LazyHorizontalGrid(
                    rows = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Dimen.size350),
                    horizontalArrangement = Arrangement.spacedBy(Dimen.size16),
                    verticalArrangement = Arrangement.spacedBy(Dimen.size12),
                ) {
                    items(state.categoryList) { category ->
                        CategoryItem(
                            categoryItem = category,
                            navigateToFeed = navigateToFeed
                        )
                    }
                }
            }
        }
    }
}


@Composable
private fun CategoryItem(
    categoryItem: UiCategoryItem,
    navigateToFeed: (String) -> Unit,
) = with(categoryItem){

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable{ navigateToFeed(category.name) }
    ) {
        image?.let {
            BaseAsyncImage(
                url = image,
                modifier = Modifier
                    .size(Dimen.size100)
                    .clip(CircleShape)
                    .background(VoltechFixedColor.lightGray)
            )

            Spacer(Modifier.height(Dimen.size12))

            Text(
                text = stringResource(categoryNameRes).capitalizeFirst(),
                style = VoltechTextStyle.title3,
                color = VoltechColor.foregroundPrimary,
                modifier = Modifier.widthIn(max = Dimen.size90),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun SetupTopBar(
    onSetupTopBar: (TopBarState) -> Unit,
    navigateToSearch: () -> Unit,
) {
    LaunchedEffect(Unit) {
        onSetupTopBar(
            TopBarState(
                titleContent = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .padding(horizontal = Dimen.size16),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextInputFieldDummy(
                            value = "",
                            onTextChanged = {  },
                            label = stringResource(R.string.search_on_voltech),
                            shape = VoltechRadius.radius24,
                            startIcon = ImageVector.vectorResource(R.drawable.ic_search),
                            onClick = { navigateToSearch() }
                        )

                        CircleIconButton(
                            onClick = {  },
                            size = Dimen.size24,
                            iconColor = VoltechColor.backgroundInverse,
                            backgroundColor = VoltechColor.backgroundTertiary,
                        )
                    }
                }
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeContentPreview(){
    HomeContent(
        state = HomeState(),
        navigateToFeed = {}
    )
}