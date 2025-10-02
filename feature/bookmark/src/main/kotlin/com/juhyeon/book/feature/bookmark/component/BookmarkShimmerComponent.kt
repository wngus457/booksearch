package com.juhyeon.book.feature.bookmark.component

import androidx.compose.foundation.lazy.LazyListScope
import com.juhyeon.book.shared.ui.system.book.BookShimmerComponent

internal fun LazyListScope.bookmarkShimmerComponent() {
    items(8) {
        BookShimmerComponent()
    }
}