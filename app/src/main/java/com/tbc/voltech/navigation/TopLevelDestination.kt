package com.tbc.voltech.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.tbc.home.presentation.navigation.HomeScreenRoute
import com.tbc.search.presentation.navigation.FeedScreenRoute
import com.tbc.search.presentation.navigation.SearchNavGraphRoute
import com.tbc.search.presentation.navigation.SearchScreenRoute
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
    SEARCH(
        selectedIcon = Icons.Rounded.Search,
        unselectedIcon = Icons.Filled.Search,
        iconTextId = R.string.search,
        route = SearchNavGraphRoute::class
    )

}
