package com.juhyeon.book.data.local.feature.book

import com.juhyeon.book.data.repository.feature.book.BookLocalDataSource
import com.juhyeon.book.data.repository.feature.book.response.BookData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookLocalDataSourceImpl @Inject constructor(
    private val bookDao: BookDao
) : BookLocalDataSource {
    override suspend fun insertBookmark(param: BookData) =
        bookDao.insertBookmark(param.toEntity())

    override suspend fun deleteBookmark(id: String) =
        bookDao.deleteBookmark(id = id)

    override fun getAllBookmarks(keyword: String): Flow<List<BookData>> =
        bookDao.getAllBookmarks("%$keyword%").map { it.map { item -> item.toData() } }

    override fun getAllBookmarkKeys(): Flow<List<String>> = bookDao.getAllBookmarkKeys()
    override fun getBookmark(id: String): Flow<BookData?> = bookDao.getBookmark(id).map { it?.toData() }
}