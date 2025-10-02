package com.juhyeon.book.data.repository.feature.book.response

import com.juhyeon.book.domain.feature.book.list.BookList

data class BookListData(
    val documents: List<BookData>,
    val meta: BookMetaData
) {
    data class BookMetaData(
        val isEnd: Boolean,
        val pageableCount: Int,
        val totalCount: Int
    )
}

internal fun BookListData.toDomain() = BookList(
    documents = documents.map { it.toDomain() },
    meta = meta.toDomain()
)

private fun BookListData.BookMetaData.toDomain() = BookList.BookMetaData(
    isEnd = isEnd,
    pageableCount = pageableCount,
    totalCount = totalCount
)