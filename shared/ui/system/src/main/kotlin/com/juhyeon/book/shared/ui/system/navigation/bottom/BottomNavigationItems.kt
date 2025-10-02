package com.juhyeon.book.shared.ui.system.navigation.bottom

import com.juhyeon.book.shared.navigation.BottomNavigationGraph
import com.juhyeon.book.shared.ui.system.icon.BottomNavSelectedBookmark
import com.juhyeon.book.shared.ui.system.icon.BottomNavSelectedSearch
import com.juhyeon.book.shared.ui.system.icon.BottomNavUnSelectedBookmark
import com.juhyeon.book.shared.ui.system.icon.BottomNavUnSelectedSearch
import kotlinx.serialization.Serializable

@Serializable
sealed class BottomNavigationItems(
    val order: Int,
    val name: String,
    val routeId: BottomNavigationGraph,
    val selectedIcon: Int,
    val unselectedIcon: Int
) {
    @Serializable
    data object Search : BottomNavigationItems(
        order = 1,
        name = "검색",
        routeId = BottomNavigationGraph.SearchGraph,
        selectedIcon = BottomNavSelectedSearch,
        unselectedIcon = BottomNavUnSelectedSearch
    )

    @Serializable
    data object Bookmark : BottomNavigationItems(
        order = 2,
        name = "즐겨찾기",
        routeId = BottomNavigationGraph.BookmarkGraph,
        selectedIcon = BottomNavSelectedBookmark,
        unselectedIcon = BottomNavUnSelectedBookmark
    )
}