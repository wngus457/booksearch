package com.juhyeon.book.data.remote.feature.book.response

import com.juhyeon.book.data.repository.feature.book.response.BookListData
import com.squareup.moshi.Json

data class BookListResponse(
    @Json(name = "documents")
    val documents: List<BookResponse>,
    @Json(name = "meta")
    val meta: BookMetaData
) {
    data class BookMetaData(
        @Json(name = "is_end")
        val isEnd: Boolean,
        @Json(name = "pageable_count")
        val pageableCount: Int,
        @Json(name = "total_count")
        val totalCount: Int
    )
}

internal fun BookListResponse.toData() = BookListData(
    documents = documents
        .filter { it.status == "정상판매" }
        .map { it.toData() },
    meta = meta.toData()
)

private fun BookListResponse.BookMetaData.toData() = BookListData.BookMetaData(
    isEnd = isEnd,
    pageableCount = pageableCount,
    totalCount = totalCount
)