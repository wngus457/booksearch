package com.juhyeon.book.data.repository.feature.book

import com.juhyeon.book.data.repository.ResultWrapper
import com.juhyeon.book.data.repository.feature.book.request.GetBookListParamData
import com.juhyeon.book.data.repository.feature.book.response.BookListData

interface BookRemoteDataSource {

    suspend fun getBookList(param: GetBookListParamData): ResultWrapper<BookListData>
}