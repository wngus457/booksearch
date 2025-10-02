package com.juhyeon.book.feature.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.juhyeon.book.shared.ui.system.navigation.bottom.BottomNavigation
import com.juhyeon.book.shared.ui.system.snackbar.BasicSnackBar
import com.juhyeon.book.shared.ui.system.theme.Palette
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    navGraph: @Composable () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val state = homeViewModel.stateFlow.collectAsState().value
    val handler = homeViewModel.eventHandler
    val scope = rememberCoroutineScope()
    val activity = LocalContext.current as? Activity
    val snackBarState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        homeViewModel.effectFlow.collect { effect ->
            when (effect) {
                is HomeContract.Effect.NavigateToFinish -> scope.launch { activity?.finish() }
                is HomeContract.Effect.ShowSnackBar -> {
                    scope.launch {
                        val job = launch {
                            snackBarState.showSnackbar(effect.message)
                        }
                        delay(2500L)
                        job.cancel()
                    }
                }
            }
        }
    }

    BackHandler(enabled = true) {
        handler(HomeContract.Event.OnBackClick)
    }

    HomeContents(
        state = state,
        navController = navController,
        snackBarState = snackBarState,
        navGraph = navGraph
    )
}

@Composable
private fun HomeContents(
    state: HomeContract.State,
    navController: NavHostController,
    snackBarState: SnackbarHostState,
    navGraph: @Composable () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation(
                navList = state.bottomNavigationItems,
                navController = navController
            )
        },
        containerColor = Palette.Common100
    ) {
        Box(Modifier.padding(it)) {
            navGraph()
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            BasicSnackBar(
                modifier = Modifier.align(Alignment.Center),
                state = snackBarState
            )
        }
    }
}