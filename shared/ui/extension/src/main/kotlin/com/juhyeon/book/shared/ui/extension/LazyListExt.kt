package com.juhyeon.book.shared.ui.extension

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.reachedBottom(): Boolean {
    val visibleItemsInfo = layoutInfo.visibleItemsInfo
    return if (layoutInfo.totalItemsCount == 0) {
        false
    } else {
        (visibleItemsInfo.last().index == layoutInfo.totalItemsCount - 2)
    }
}