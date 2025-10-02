package com.juhyeon.book.feature.bookmark

import com.juhyeon.book.domain.feature.book.Book
import com.juhyeon.book.shared.core.mvi.UiEffect
import com.juhyeon.book.shared.core.mvi.UiEvent
import com.juhyeon.book.shared.core.mvi.UiState
import com.juhyeon.book.shared.navigation.BookDetail
import com.juhyeon.book.shared.ui.system.sort.FilterBookmark
import com.juhyeon.book.shared.ui.system.sort.SortBookmark

interface BookmarkContract {
    sealed interface Event : UiEvent {
        data object OnRefresh : Event
        data class OnSearchBookList(val keyword: String) : Event
        data class OnDelectBookmark(val id: String) : Event
        data class OnChangeSort(val sort: SortBookmark) : Event
        data class OnChangeFilter(val filter: FilterBookmark) : Event
        data class OnBookClick(val book: Book) : Event
    }

    data class State(
        val viewState: BookmarkViewState
    ) : UiState {
        sealed interface BookmarkViewState {
            data object Loading : BookmarkViewState
            data class Success(val data: List<Book>) : BookmarkViewState
            data object Empty : BookmarkViewState
        }

        data class SearchBookmark(
            val keyword: String = "",
            val sort: SortBookmark = SortBookmark.ASCENDING,
            val filter: FilterBookmark = FilterBookmark.ALL
        )
    }

    sealed interface Effect : UiEffect {
        data object ScrollTop : Effect
        data class NavigateToDetail(val bookDetail: BookDetail) : Effect
    }
}