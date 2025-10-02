package com.juhyeon.book.shared.ui.system.sort

interface BasicSortItems {
    val key: String
    val value: String
}

enum class SortSearch(override val key: String, override val value: String) : BasicSortItems {
    ACCURACY("accuracy", "정확도순"),
    LATEST("latest", "발간일순")
}

enum class SortBookmark(override val key: String, override val value: String) : BasicSortItems {
    ASCENDING("ascending", "오름차순(제목)"),
    DESCENDING("descending", "내림차순(제목)")
}

enum class FilterBookmark(override val key: String, override val value: String) : BasicSortItems {
    ALL("all", "전체"),
    WON10000("won10000", "0~10,000원"),
    WON20000("won20000", "10,000~20,000원"),
    WON30000("won30000", "20,000~30,000원"),
    WON40000("won40000", "30,000~40,000원"),
    MAX("max", "40,000원~")
}