package com.juhyeon.book.feature.home

import com.juhyeon.book.shared.core.mvi.UiEffect
import com.juhyeon.book.shared.core.mvi.UiEvent
import com.juhyeon.book.shared.core.mvi.UiState
import com.juhyeon.book.shared.ui.system.navigation.bottom.BottomNavigationItems

interface HomeContract {
    sealed interface Event : UiEvent {
        data object OnBackClick : Event
    }

    data class State(
        val bottomNavigationItems: List<BottomNavigationItems>
    ) : UiState

    sealed interface Effect : UiEffect {
        data object NavigateToFinish : Effect
        data class ShowSnackBar(val message: String) : Effect
    }
}