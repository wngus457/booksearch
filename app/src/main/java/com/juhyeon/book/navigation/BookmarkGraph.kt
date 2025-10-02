package com.juhyeon.book.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import com.juhyeon.book.feature.bookmark.BookmarkScreen
import com.juhyeon.book.shared.navigation.BottomNavigationGraph
import com.juhyeon.book.shared.ui.extension.noAnimComposable
import com.juhyeon.book.shared.ui.system.navigation.bottom.BottomNavigationItems

fun NavGraphBuilder.bookmarkGraph(
    navController: NavHostController
) {
    navigation<BottomNavigationItems.Bookmark>(
        startDestination = BottomNavigationGraph.BookmarkGraph
    ) {
        noAnimComposable<BottomNavigationGraph.BookmarkGraph> {
            BookmarkScreen(
                navController = navController
            )
        }
    }
}