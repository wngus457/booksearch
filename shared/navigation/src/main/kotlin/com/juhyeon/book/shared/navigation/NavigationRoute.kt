package com.juhyeon.book.shared.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Splash

@Serializable
data object Home

@Serializable
sealed class BottomNavigationGraph {
    @Serializable
    data object SearchGraph : BottomNavigationGraph()

    @Serializable
    data object BookmarkGraph : BottomNavigationGraph()
}

@Serializable
data class BookDetail(
    val id: String,
    val title: String,
    val contents: String,
    val url: String,
    val isbn: String,
    val datetime: String,
    val authors: String,
    val publisher: String,
    val translators: String,
    val price: Int,
    val salePrice: Int,
    val thumbnail: String,
    val status: String
)