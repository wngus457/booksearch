package com.juhyeon.book.data.remote.service

import com.juhyeon.book.data.remote.feature.book.response.BookListResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface BookService {

    @GET("v3/search/book")
    suspend fun getBookList(
        @QueryMap params: Map<String, String>
    ): BookListResponse
}