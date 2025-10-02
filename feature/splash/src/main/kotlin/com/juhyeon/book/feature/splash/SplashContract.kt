package com.juhyeon.book.feature.splash

import com.juhyeon.book.shared.core.mvi.UiEffect
import com.juhyeon.book.shared.core.mvi.UiEvent
import com.juhyeon.book.shared.core.mvi.UiState

interface SplashContract {

    sealed interface Event : UiEvent

    data object State : UiState

    sealed interface Effect : UiEffect {
        data object NavigateToHome : Effect
    }
}