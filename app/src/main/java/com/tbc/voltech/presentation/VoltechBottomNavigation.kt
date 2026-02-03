package com.tbc.voltech.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import com.tbc.core_ui.theme.Dimen
import com.tbc.core_ui.theme.VoltechColor
import com.tbc.voltech.navigation.TopLevelDestination

@OptIn(ExperimentalMaterial3Api::class)
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
        BottomAppBar(
            windowInsets = WindowInsets.navigationBars,
            containerColor = VoltechColor.backgroundSecondary,
        ) {
            destinations.forEach { destination ->
                val selected = currentDestination.isRouteInHierarchy(destination.route)

                NavigationBarItem(

                    icon = {
                        Icon(
                            modifier = Modifier.size(Dimen.size24),
                            imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                            contentDescription = destination.name,
                            tint = if (selected) VoltechColor.foregroundAccent else VoltechColor.foregroundPrimary
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(destination.iconTextId),
                            color = if (selected) VoltechColor.foregroundAccent else VoltechColor.foregroundPrimary,
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                        )
                    },
                    selected = selected,
                    onClick = {
                        if (!selected)
                            onNavigateToDestination(destination)
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = VoltechColor.foregroundAccent,
                        unselectedIconColor = VoltechColor.foregroundPrimary,
                        unselectedTextColor = VoltechColor.foregroundPrimary,
                        indicatorColor = VoltechColor.foregroundAccent.copy(alpha = 0.12f)
                    )
                )
            }
        }
    }
}