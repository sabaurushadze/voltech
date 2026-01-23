package com.tbc.search.presentation.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalResources
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.designsystem.components.textfield.TextInputField
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechColor
import com.tbc.core.designsystem.theme.VoltechTextStyle
import com.tbc.search.presentation.R

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onShowSnackBar: (String) -> Unit,
    navigateToFeed: (String) -> Unit,
) {
    val context = LocalResources.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LogInContent(
        state = state,
        onEvent = viewModel::onEvent
    )

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { sideEffect ->
            when (sideEffect) {
                is SearchSideEffect.ShowSnackBar -> {
                    val error = context.getString(sideEffect.errorRes)
                    onShowSnackBar(error)
                }

                is SearchSideEffect.NavigateToFeed -> navigateToFeed(sideEffect.query)
            }
        }
    }
}


@Composable
private fun LogInContent(
    state: SearchState,
    onEvent: (SearchEvent) -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextInputField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimen.size16),
            value = state.query,
            onTextChanged = { onEvent(SearchEvent.QueryChanged(it)) },
            label = stringResource(R.string.search_on_voltech),
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email,
        )

        Spacer(modifier = Modifier.height(Dimen.size16))

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(state.titles) { item ->
                SearchItem(
                    title = item.title,
                    onSearchItemClick = {
                        onEvent(SearchEvent.NavigateToFeedWithQuery(item.title))
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
//
//@Preview(showBackground = true)
//@Composable
//fun SimpleComposablePreview() {
//    VoltechTheme() {
//        LogInContent(
//            state = SearchState(
//                isLoading = true,
//                query = "123",
//            ),
//            onEvent = {},
//        )
//    }
//}