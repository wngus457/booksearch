package com.juhyeon.book.feature.search.component

import androidx.compose.foundation.lazy.LazyListScope
import com.juhyeon.book.shared.ui.system.book.BookShimmerComponent

internal fun LazyListScope.searchShimmerComponent() {
    items(8) {
        BookShimmerComponent()
    }
}