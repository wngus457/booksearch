package com.juhyeon.book.data.remote.feature.book.response

import com.juhyeon.book.data.repository.feature.book.response.BookData
import com.squareup.moshi.Json

data class BookResponse(
    @Json(name = "title")
    val title: String,
    @Json(name = "contents")
    val contents: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "isbn")
    val isbn: String,
    @Json(name = "datetime")
    val datetime: String,
    @Json(name = "authors")
    val authors: List<String>,
    @Json(name = "publisher")
    val publisher: String,
    @Json(name = "translators")
    val translators: List<String>,
    @Json(name = "price")
    val price: Int,
    @Json(name = "sale_price")
    val salePrice: Int,
    @Json(name = "thumbnail")
    val thumbnail: String,
    @Json(name = "status")
    val status: String
)

internal fun BookResponse.toData() = BookData(
    id = isbn + salePrice,
    title = title,
    contents = contents,
    url = url,
    isbn = isbn,
    datetime = datetime,
    authors = authors,
    publisher = publisher,
    translators = translators,
    price = price,
    salePrice = salePrice,
    thumbnail = thumbnail,
    status = status
)
