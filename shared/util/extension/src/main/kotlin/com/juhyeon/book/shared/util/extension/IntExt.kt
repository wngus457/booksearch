package com.juhyeon.book.shared.util.extension

import java.text.DecimalFormat

fun Int.applyCommaFormat(): String = DecimalFormat("###,###").format(this)