package com.juhyeon.book.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.juhyeon.book.shared.ui.system.navigation.bottom.BottomNavigationItems

@Composable
fun BottomNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationItems.Search,
        enterTransition = { EnterTransition.None },
        exitTransition = { fadeOut(targetAlpha = 5000f) },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { fadeOut(targetAlpha = 5000f) }
    ) {
        searchGraph(navController)
        bookmarkGraph(navController)
        commonNavGraph(navController)
    }
}