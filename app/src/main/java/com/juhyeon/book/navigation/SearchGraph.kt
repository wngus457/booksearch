package com.juhyeon.book.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.juhyeon.book.feature.search.SearchScreen
import com.juhyeon.book.shared.navigation.BottomNavigationGraph
import com.juhyeon.book.shared.ui.extension.noAnimComposable
import com.juhyeon.book.shared.ui.system.navigation.bottom.BottomNavigationItems

fun NavGraphBuilder.searchGraph(
    navController: NavHostController
) {
    navigation<BottomNavigationItems.Search>(
        startDestination = BottomNavigationGraph.SearchGraph
    ) {
        noAnimComposable<BottomNavigationGraph.SearchGraph> {
            SearchScreen(
                navController = navController
            )
        }
    }
}