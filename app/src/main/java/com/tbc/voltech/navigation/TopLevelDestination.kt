package com.tbc.voltech.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.tbc.presentation.navigation.HomeScreenRoute
import com.tbc.voltech.R
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @param:StringRes val iconTextId: Int,
    val route: KClass<*>,
) {
    HOME(
        selectedIcon = Icons.Rounded.Home,
        unselectedIcon = Icons.Outlined.Home,
        iconTextId = R.string.home,
        route = HomeScreenRoute::class
    ),
//    PARTS(
//        selectedIcon = Icons.Rounded.LaptopWindows,
//        unselectedIcon = Icons.Filled.LaptopWindows,
//        iconTextId = R.string.parts,
//        route = aqiqnebapcpartroute::class
//    )
}
