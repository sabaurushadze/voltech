package com.tbc.voltech.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.tbc.home.presentation.navigation.HomeScreenRoute
import com.tbc.profile.presentation.navigation.ProfileNavGraphRoute
import com.tbc.resource.R
import com.tbc.search.presentation.navigation.SearchNavGraphRoute
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @param:StringRes val iconTextId: Int,
    val route: KClass<*>,
) {
    HOME(
        selectedIcon = Icons.Outlined.Home,
        unselectedIcon = Icons.Outlined.Home,
        iconTextId = R.string.home,
        route = HomeScreenRoute::class
    ),
    PROFILE(
        selectedIcon = Icons.Outlined.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        iconTextId = R.string.profile,
        route = ProfileNavGraphRoute::class
    ),
    SEARCH(
        selectedIcon = Icons.Rounded.Search,
        unselectedIcon = Icons.Filled.Search,
        iconTextId = R.string.search,
        route = SearchNavGraphRoute::class
    ),
}
