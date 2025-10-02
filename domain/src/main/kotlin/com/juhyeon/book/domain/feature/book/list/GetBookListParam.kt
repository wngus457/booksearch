package com.juhyeon.book.domain.feature.book.list

data class GetBookListParam(
    val query: String,
    val sort: String = "accuracy",
    val page: Int = 1
)
