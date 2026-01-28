package com.tbc.core.designsystem.components.topappbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.tbc.core.designsystem.R
import com.tbc.core.designsystem.components.textfield.TextInputField
import com.tbc.core.designsystem.theme.Dimen
import com.tbc.core.designsystem.theme.VoltechRadius

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    query: String,
    onTextChanged: (String) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextInputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = Dimen.size16, bottom = Dimen.size16)
                        .focusRequester(focusRequester),
                    value = query,
                    onTextChanged = { onTextChanged(it) },
                    label = stringResource(R.string.search_on_voltech),
                    imeAction = ImeAction.Next,
                    shape = VoltechRadius.radius24,
                    keyboardType = KeyboardType.Email,
                    startIcon = ImageVector.vectorResource(R.drawable.ic_search)
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = VoltechTopAppBarDefaults.primaryColors,
        modifier = modifier,

    )
}