package com.tbc.core_ui.components.topbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import com.tbc.core_ui.components.button.CircleIconButton
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.core_ui.theme.VoltechTextStyle
import com.tbc.resource.R

@Composable
@ExperimentalMaterial3Api
fun TopBarContent(config: TopBarConfig) {
    TopAppBar(
        colors = VoltechTopAppBarDefaults.secondaryColors,
        title = {
            config.searchContent?.let {
                config.searchContent.invoke()
            } ?: run {
                config.title?.let {
                    Text(
                        text = stringResource(it),
                        style = VoltechTextStyle.title2,
                        color = VoltechColor.foregroundPrimary
                    )
                }
            }
        },
        navigationIcon = {
            if (config.showBackButton && config.backButtonAction != null) {
                IconButton(onClick = config.backButtonAction) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_arrow_back),
                        tint = VoltechColor.foregroundPrimary,
                        contentDescription = null
                    )
                }
            }
        },
        actions = {
            config.actions.forEach { action ->
                CircleIconButton(
                    onClick = { action.onClick() },
                    iconRes = action.iconRes,
                    size = Dimen.size24,
                    iconColor = VoltechColor.backgroundInverse,
                    backgroundColor = VoltechColor.backgroundTertiary,
                )
            }
        }
    )
}

