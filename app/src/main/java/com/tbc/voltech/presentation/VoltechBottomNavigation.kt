package com.tbc.voltech.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import com.tbc.designsystem.theme.Dimen
import com.tbc.designsystem.theme.VoltechColor
import com.tbc.voltech.navigation.TopLevelDestination

@Composable
fun VoltechBottomNavigation(
    destinations: List<TopLevelDestination>,
    currentDestination: NavDestination?,
    visible: Boolean,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + expandVertically(),
        exit = ExitTransition.None
    ) {

        NavigationBar(
            modifier = Modifier.clip(
                shape = RoundedCornerShape(
                    topStart = Dimen.size16,
                    topEnd = Dimen.size16
                )
            )
        ) {
            destinations.forEach { destination ->
                val selected = currentDestination.isRouteInHierarchy(destination.route)

                NavigationBarItem(

                    icon = {
                        Icon(
                            imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                            contentDescription = destination.name,
                            tint = if (selected) VoltechColor.primary else VoltechColor.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(destination.iconTextId),
                            color = if (selected) VoltechColor.primary else VoltechColor.onBackground,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        )
                    },
                    selected = selected,
                    onClick = {
                        if (!selected)
                            onNavigateToDestination(destination)
                    },
                )
            }
        }
    }
}