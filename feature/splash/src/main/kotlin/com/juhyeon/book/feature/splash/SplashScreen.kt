package com.juhyeon.book.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.juhyeon.book.shared.navigation.Home
import com.juhyeon.book.shared.ui.system.theme.SplashImage

@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
) {
    val state = splashViewModel.stateFlow.collectAsState().value
    val handler = splashViewModel.eventHandler

    LaunchedEffect(true) {
        splashViewModel.effectFlow.collect { effect ->
            when (effect) {
                is SplashContract.Effect.NavigateToHome -> navController.navigate(Home) { popUpTo(0) { inclusive = true } }
            }
        }
    }

    SplashContents()
}

@Composable
private fun SplashContents() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentDescription = "splash Image",
                painter = painterResource(SplashImage),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashContentsPreview() {
    SplashContents()
}