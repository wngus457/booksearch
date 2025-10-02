package com.juhyeon.book.feature.detail

import com.juhyeon.book.domain.feature.book.Book
import com.juhyeon.book.shared.core.mvi.UiEffect
import com.juhyeon.book.shared.core.mvi.UiEvent
import com.juhyeon.book.shared.core.mvi.UiState

interface BookDetailContract {
    sealed interface Event : UiEvent {
        data object OnBackClick : Event
        data class OnChangeBookmark(val book: Book) : Event
    }

    data class State(
        val viewState: BookDetailViewState
    ) : UiState {
        sealed interface BookDetailViewState {
            data object Loading : BookDetailViewState
            data class Success(val book: Book) : BookDetailViewState
        }
    }

    sealed interface Effect : UiEffect {
        data object NavigateToBack : Effect
    }
}