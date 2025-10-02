package com.juhyeon.book.data.repository.feature.book.request

import com.juhyeon.book.domain.feature.book.list.GetBookListParam

data class GetBookListParamData(
    val query: String,
    val sort: String,
    val page: Int
)

internal fun GetBookListParam.toData() = GetBookListParamData(
    query = query,
    sort = sort,
    page = page
)
