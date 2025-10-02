package com.juhyeon.book.data.remote.feature.book

import com.juhyeon.book.data.remote.feature.book.response.toData
import com.juhyeon.book.data.remote.safeApiCall
import com.juhyeon.book.data.remote.service.BookService
import com.juhyeon.book.data.repository.ResultWrapper
import com.juhyeon.book.data.repository.feature.book.BookRemoteDataSource
import com.juhyeon.book.data.repository.feature.book.request.GetBookListParamData
import com.juhyeon.book.data.repository.feature.book.response.BookListData
import com.juhyeon.book.shared.util.common.LogHelper
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class BookRemoteDataSourceImpl @Inject constructor(
    private val logHelper: LogHelper,
    private val bookService: BookService
) : BookRemoteDataSource {
    override suspend fun getBookList(param: GetBookListParamData): ResultWrapper<BookListData> {
        val request = hashMapOf(
            "query" to param.query,
            "sort" to param.sort,
            "page" to param.page.toString(),
            "size" to "20"
        )
        return safeApiCall(Dispatchers.IO, logHelper) {
            bookService.getBookList(request).toData()
        }
    }
}