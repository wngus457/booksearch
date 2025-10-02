package com.juhyeon.book.feature.splash

import androidx.lifecycle.viewModelScope
import com.juhyeon.book.shared.core.mvi.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {
    override fun initState() = SplashContract.State

    override fun handleEvent(event: SplashContract.Event) {
        when (event) {
            else -> { }
        }
    }

    init {
        viewModelScope.launch {
            delay(2000)
            reducer.setEffect(SplashContract.Effect.NavigateToHome)
        }
    }
}