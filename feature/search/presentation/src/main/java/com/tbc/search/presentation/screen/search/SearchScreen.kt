package com.tbc.search.presentation.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.designsystem.components.topappbar.SearchAppBar
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechTextStyle
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.compositionlocal.LocalTopBarState
import com.tbc.core.presentation.extension.collectSideEffect
import com.tbc.search.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToFeed: (String) -> Unit,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
) {
    val topBarState = LocalTopBarState.current
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    LaunchedEffect(Unit) {
        topBarState.setTopBar(
            content = {
                SearchAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester),
                    query = state.query,
                    onTextChanged = { viewModel.onEvent(SearchEvent.QueryChanged(it)) },
                    scrollBehavior = topAppBarScrollBehavior,
                    focusRequester = focusRequester,
                )
            },
            behavior = topAppBarScrollBehavior,
            key = "SearchScreen"
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            topBarState.clearTopBar("SearchScreen")
        }
    }

    viewModel.sideEffect.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SearchSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }

            is SearchSideEffect.NavigateToFeed -> navigateToFeed(sideEffect.query)
        }
    }

    LogInContent(
        state = state,
        onEvent = viewModel::onEvent,
    )

}


@Composable
private fun LogInContent(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VoltechColor.background)
            .systemBarsPadding()
    ) {
        if (state.query.isEmpty()) {
            Text(
                modifier = Modifier.padding(horizontal = Dimen.size16),
                text = stringResource(R.string.recent_searchs)
            )

            Spacer(modifier = Modifier.height(Dimen.size16))

            LazyColumn {
                items(state.recentSearchList) { item ->
                    RecentSearchItem(
                        title = item,
                        onRecentSearchItemClick = {
                            onEvent(SearchEvent.NavigateToFeedWithQuery(item))
                        },
                        onRemoveClick = {
                            onEvent(SearchEvent.RemoveRecentSearch(item))
                        }
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.titles) { item ->
                SearchItem(
                    title = item.title,
                    onSearchItemClick = {
                        onEvent(SearchEvent.NavigateToFeedWithQuery(item.title))
                        onEvent(SearchEvent.SaveRecentSearch(item.title))
                    }
                )
            }
        }
    }
}

@Composable
private fun SearchItem(
    title: String,
    onSearchItemClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSearchItemClick() }
            .padding(Dimen.size16)
    ) {
        Text(
            text = title,
            style = VoltechTextStyle.body16Bold,
            color = VoltechColor.onBackground
        )
    }
}

@Composable
private fun RecentSearchItem(
    title: String,
    onRecentSearchItemClick: () -> Unit,
    onRemoveClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onRecentSearchItemClick() }
            .padding(horizontal = Dimen.size16),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = VoltechTextStyle.body16Bold,
            color = VoltechColor.onBackground
        )

        IconButton(
            onClick = { onRemoveClick() }
        ) {
            Icon(
                modifier = Modifier.size(Dimen.size12),
                painter = painterResource(R.drawable.ic_remove_x),
                contentDescription = null
            )
        }
    }
}