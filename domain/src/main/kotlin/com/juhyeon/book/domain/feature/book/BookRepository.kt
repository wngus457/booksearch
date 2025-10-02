package com.juhyeon.book.domain.feature.book

import com.juhyeon.book.domain.Result
import com.juhyeon.book.domain.feature.book.list.BookList
import com.juhyeon.book.domain.feature.book.list.GetBookListParam
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    fun getBookList(param: GetBookListParam): Flow<Result<BookList>>


    suspend fun insertBookmark(param: Book)

    suspend fun deleteBookmark(id: String)

    fun getAllBookmarks(keyword: String): Flow<Result<List<Book>>>

    fun getAllBookmarkKeys(): Flow<Result<List<String>>>

    fun getBookmark(id: String): Flow<Result<Book?>>
}