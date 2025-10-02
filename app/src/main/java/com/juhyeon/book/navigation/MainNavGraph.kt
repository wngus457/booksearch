package com.juhyeon.book.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.juhyeon.book.feature.home.HomeScreen
import com.juhyeon.book.feature.splash.SplashScreen
import com.juhyeon.book.shared.navigation.Home
import com.juhyeon.book.shared.navigation.Splash

@Composable
fun MainNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash> {
            SplashScreen(
                navController = navController
            )
        }
        composable<Home> {
            val homeNavController = rememberNavController()
            HomeScreen(
                navController = homeNavController,
                navGraph = { BottomNavGraph(navController = homeNavController) }
            )
        }
    }
}