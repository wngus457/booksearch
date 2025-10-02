package com.juhyeon.book.data.local.feature.book

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.juhyeon.book.data.repository.feature.book.response.BookData

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val contents: String,
    val url: String,
    val isbn: String,
    val datetime: String,
    val authors: String,
    val publisher: String,
    val translators: String,
    val price: Int,
    val salePrice: Int,
    val thumbnail: String,
    val status: String
)

internal fun BookEntity.toData() = BookData(
    id = id,
    title = title,
    contents = contents,
    url = url,
    isbn = isbn,
    datetime = datetime,
    authors = authors.split(",").map { it },
    publisher = publisher,
    translators = translators.split(",").map { it },
    price = price,
    salePrice = salePrice,
    thumbnail = thumbnail,
    status = status
)

internal fun BookData.toEntity() = BookEntity(
    id = id,
    title = title,
    contents = contents,
    url = url,
    isbn = isbn,
    datetime = datetime,
    authors = authors.joinToString(","),
    publisher = publisher,
    translators = translators.joinToString(","),
    price = price,
    salePrice = salePrice,
    thumbnail = thumbnail,
    status = status
)