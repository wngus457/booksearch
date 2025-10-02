package com.juhyeon.book.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.juhyeon.book.feature.detail.BookDetailScreen
import com.juhyeon.book.shared.navigation.BookDetail
import com.juhyeon.book.shared.ui.extension.slideLeftRightComposable

fun NavGraphBuilder.commonNavGraph(
    navController: NavHostController
) {
    slideLeftRightComposable<BookDetail> {
        BookDetailScreen(navController = navController)
    }
}