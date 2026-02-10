package com.tbc.selling.presentation.screen.my_items

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.extension.collectSideEffect
import com.tbc.core_ui.components.empty_state.EmptyState
import com.tbc.core_ui.components.radiobutton.VoltechRadioButtonDefaults
import com.tbc.core_ui.components.topbar.TopBarAction
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R

@Composable
fun MyItemsScreen(
    viewModel: MyItemsViewModel = hiltViewModel(),
    onSetupTopBar: (TopBarState) -> Unit,
    navigateToAddItem: () -> Unit,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    SetupTopBar(onSetupTopBar)

    LaunchedEffect(Unit) {
        state.user?.let { user ->
            viewModel.onEvent(MyItemsEvent.GetMyItems(uid = user.uid))
        }
    }

    viewModel.sideEffect.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is MyItemsSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }

            MyItemsSideEffect.NavigateToAddItem -> { navigateToAddItem() }
        }
    }


    if (state.myItems.isEmpty()) {
        EmptyState(
            title = "You currently have no listings",
            buttonText = "Add item",
            onButtonClick = { viewModel.onEvent(MyItemsEvent.NavigateToAddItem) },
        )
    } else {
        MyItemsContent(
            state = state,
            onEvent = viewModel::onEvent,
        )
    }

}

@Composable
private fun MyItemsContent(
    state: MyItemsState,
    onEvent: (MyItemsEvent) -> Unit,
) {

}


@Composable
private fun SetupTopBar(
    onSetupTopBar: (TopBarState) -> Unit,
) {
    val title = stringResource(id = R.string.my_items)

    LaunchedEffect(Unit) {
        onSetupTopBar(
            TopBarState(
                title = title,
            )
        )
    }
}