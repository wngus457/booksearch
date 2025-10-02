package com.juhyeon.book.domain.feature.book.list

import com.juhyeon.book.domain.feature.book.Book

data class BookList(
    val documents: List<Book>,
    val meta: BookMetaData
) {
    data class BookMetaData(
        val isEnd: Boolean,
        val pageableCount: Int,
        val totalCount: Int
    )
}