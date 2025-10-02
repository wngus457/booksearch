package com.juhyeon.book.feature.search

import com.juhyeon.book.domain.feature.book.Book
import com.juhyeon.book.domain.feature.book.list.BookList
import com.juhyeon.book.shared.core.mvi.UiEffect
import com.juhyeon.book.shared.core.mvi.UiEvent
import com.juhyeon.book.shared.core.mvi.UiState
import com.juhyeon.book.shared.navigation.BookDetail
import com.juhyeon.book.shared.ui.system.sort.SortSearch

interface SearchContract {
    sealed interface Event : UiEvent {
        data class OnSearchBookList(val keyword: String) : Event
        data class OnChangeSort(val sort: SortSearch) : Event
        data object OnChangePage : Event
        data class OnChangeBookmark(val isBookmark: Boolean, val book: Book) : Event
        data class OnBookClick(val book: Book) : Event
        data object OnBookmarkRefresh : Event
    }

    data class State(
        val viewState: SearchViewState
    ) : UiState {
        sealed interface SearchViewState {
            data object Init : SearchViewState
            data object Loading : SearchViewState
            data class Success(val meta: BookList.BookMetaData, val books: List<Book>) : SearchViewState
            data class Error(val message: String) : SearchViewState
            data object Empty : SearchViewState
        }

        data class SearchData(
            val page: Int = 1,
            val keyword: String = "",
            val sort: SortSearch = SortSearch.ACCURACY
        )
    }

    sealed interface Effect : UiEffect {
        data class NavigateToDetail(val bookDetail: BookDetail) : Effect
        data object ScrollToTop : Effect
    }
}