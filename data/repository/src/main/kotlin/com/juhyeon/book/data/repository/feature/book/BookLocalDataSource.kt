package com.juhyeon.book.data.repository.feature.book

import com.juhyeon.book.data.repository.feature.book.response.BookData
import kotlinx.coroutines.flow.Flow

interface BookLocalDataSource {

    suspend fun insertBookmark(param: BookData)

    suspend fun deleteBookmark(id: String)

    fun getAllBookmarks(keyword: String): Flow<List<BookData>>

    fun getAllBookmarkKeys(): Flow<List<String>>

    fun getBookmark(id: String): Flow<BookData?>
}