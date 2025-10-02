package com.juhyeon.book.feature.home

import com.juhyeon.book.shared.core.mvi.BaseViewModel
import com.juhyeon.book.shared.ui.system.navigation.bottom.BottomNavigationItems
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    private var lastBackPressedTimeMillis = 0L

    override fun initState() = HomeContract.State(
        bottomNavigationItems = BottomNavigationItems::class
            .sealedSubclasses
            .map { it.objectInstance as BottomNavigationItems }
            .sortedBy { it.order }
    )

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnBackClick -> onBackPressed()
        }
    }

    private fun onBackPressed() {
        closeScreenOrSaveBackPressedTime()
    }

    private fun closeScreenOrSaveBackPressedTime() {
        if (getMillisFromLastBackPressedTime() <= MILLIS_OF_CLOSING_SCREEN) {
            reducer.setEffect(HomeContract.Effect.NavigateToFinish)
        } else {
            lastBackPressedTimeMillis = System.currentTimeMillis()
            reducer.setEffect(HomeContract.Effect.ShowSnackBar(MESSAGE_CLOSE_SCREEN))
        }
    }

    private fun getMillisFromLastBackPressedTime() = System.currentTimeMillis() - lastBackPressedTimeMillis

    companion object {
        private const val MILLIS_OF_CLOSING_SCREEN = 2000L
        private const val MESSAGE_CLOSE_SCREEN = "'뒤로'버튼을 한번 더 누르면 종료합니다."
    }
}