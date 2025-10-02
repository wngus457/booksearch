package com.juhyeon.book.shared.ui.system.navigation.bottom

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.currentBackStackEntryAsState
import com.juhyeon.book.shared.ui.system.theme.Palette
import com.juhyeon.book.shared.ui.system.theme.normal

@Composable
fun <T : BottomNavigationItems> BottomNavigation(
    navList: List<T>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route ?: ""
    val showBottomNavigation = currentRoute.contains("Graph")
    val navOptionsBuilder = NavOptions
        .Builder()
        .setLaunchSingleTop(true)
        .setRestoreState(true)

    if (showBottomNavigation) {
        NavigationBar(
            modifier = modifier,
            containerColor = Palette.Common100,
            contentColor = Palette.Common100,
            tonalElevation = 8.dp
        ) {
            navList.forEach { item ->
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(
                                id = if (currentDestination?.parent?.route == item::class.qualifiedName) item.selectedIcon else item.unselectedIcon
                            ),
                            contentDescription = item.name,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.name,
                            color = if (currentDestination?.parent?.route == item::class.qualifiedName) Palette.Neutral10 else Palette.Neutral65,
                            style = MaterialTheme.typography.normal(12)
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = Palette.Common100,
                        selectedIconColor = Palette.Neutral10,
                        selectedTextColor = Palette.Neutral10,
                        unselectedIconColor = Palette.Neutral65,
                        unselectedTextColor = Palette.Neutral65
                    ),
                    selected = currentDestination?.parent?.route == item::class.qualifiedName,
                    onClick = {
                        navOptionsBuilder.setPopUpTo(
                            destinationId = 0,
                            inclusive = true,
                            saveState = true
                        )
                        navController.navigate(
                            route = item.routeId,
                            navOptions = navOptionsBuilder.build()
                        )
                    }
                )
            }
        }
    }
}