package com.juhyeon.book.data.repository.feature.book

import com.juhyeon.book.data.repository.feature.book.request.toData
import com.juhyeon.book.data.repository.feature.book.response.toData
import com.juhyeon.book.data.repository.feature.book.response.toDomain
import com.juhyeon.book.data.repository.toDomain
import com.juhyeon.book.domain.Result
import com.juhyeon.book.domain.feature.book.Book
import com.juhyeon.book.domain.feature.book.BookRepository
import com.juhyeon.book.domain.feature.book.list.BookList
import com.juhyeon.book.domain.feature.book.list.GetBookListParam
import com.juhyeon.book.domain.mapToResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val bookRemoteDataSource: BookRemoteDataSource,
    private val bookLocalDataSource: BookLocalDataSource
) : BookRepository {
    override fun getBookList(param: GetBookListParam): Flow<Result<BookList>> = flow {
        emit(Result.Loading)
        val result = bookRemoteDataSource.getBookList(param.toData())
        emit(result.toDomain { it.toDomain() })
    }

    override suspend fun insertBookmark(param: Book) =
        bookLocalDataSource.insertBookmark(param.toData())

    override suspend fun deleteBookmark(param: String) =
        bookLocalDataSource.deleteBookmark(param)

    override fun getAllBookmarks(keyword: String): Flow<Result<List<Book>>> =
        bookLocalDataSource.getAllBookmarks(keyword)
            .map { it.map { item -> item.toDomain() } }
            .mapToResult()

    override fun getAllBookmarkKeys(): Flow<Result<List<String>>> =
        bookLocalDataSource.getAllBookmarkKeys()
            .map { it }
            .mapToResult()

    override fun getBookmark(id: String): Flow<Result<Book?>> =
        bookLocalDataSource.getBookmark(id)
            .map { it?.toDomain() }
            .mapToResult()
}