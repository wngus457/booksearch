package com.juhyeon.book.shared.util.extension

import java.text.SimpleDateFormat
import java.util.Locale

fun String.iso8601ToFormat(): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.KOREA)
    val currentDate = format.parse(this)
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
    return dateFormat.format(currentDate)
}