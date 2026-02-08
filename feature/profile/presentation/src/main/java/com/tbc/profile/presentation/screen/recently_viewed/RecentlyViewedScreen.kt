package com.tbc.profile.presentation.screen.recently_viewed

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tbc.core_ui.components.topbar.TopBarAction
import com.tbc.core_ui.components.topbar.TopBarState
import com.tbc.resource.R
import kotlin.Unit

@Composable
fun RecentlyViewedScreen(
    viewModel: RecentlyViewedViewModel = hiltViewModel(),
    navigateToProfile: () -> Unit,
    onSetupTopBar: (TopBarState) -> Unit,
){
    SetupTopBar(
        onSetupTopBar = onSetupTopBar,
        navigateToProfile = navigateToProfile
    )

    RecentlyViewedContent()
}

@Composable
private fun RecentlyViewedContent(){

}

@Composable
private fun SetupTopBar(
    onSetupTopBar: (TopBarState) -> Unit,
    navigateToProfile: () -> Unit,
) {
    val title = stringResource(id = R.string.recently_viewed)

    LaunchedEffect(Unit) {
        onSetupTopBar(
            TopBarState(
                title = title,
                navigationIcon = TopBarAction(
                    icon = R.drawable.ic_arrow_back,
                    onClick = { navigateToProfile() }
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun RecentlyViewedScreenPreview(){
    RecentlyViewedContent()
}