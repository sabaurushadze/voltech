package com.tbc.voltech.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val iconTextId: Int,
    val route: KClass<*>,
) {
//    HOME(
//        selectedIcon = Icons.Rounded.Home,
//        unselectedIcon = Icons.Outlined.Home,
//        iconTextId = R.string.home,
//        route = HomeScreenRoute::class
//    ),
//    PARTS(
//        selectedIcon = Icons.Rounded.Pc,
//        unselectedIcon = Icons.Filled.Pc,
//        iconTextId = R.string.parts,
//        route = PartsScreenRoute::class
//    )
}
