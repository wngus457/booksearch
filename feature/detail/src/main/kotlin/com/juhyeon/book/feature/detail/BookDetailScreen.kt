package com.juhyeon.book.feature.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.juhyeon.book.domain.feature.book.Book
import com.juhyeon.book.feature.detail.component.BookDetailComponent
import com.juhyeon.book.feature.detail.component.BookDetailShimmerComponent
import com.juhyeon.book.shared.ui.system.navigation.top.TopNavigationBackTitle
import com.juhyeon.book.shared.ui.system.theme.Palette

@Composable
fun BookDetailScreen(
    navController: NavHostController,
    bookDetailViewModel: BookDetailViewModel = hiltViewModel()
) {
    val state = bookDetailViewModel.stateFlow.collectAsState().value
    val handler = bookDetailViewModel.eventHandler

    LaunchedEffect(true) {
        bookDetailViewModel.effectFlow.collect { effect ->
            when (effect) {
                is BookDetailContract.Effect.NavigateToBack -> navController.popBackStack()
            }
        }
    }

    BookDetailContents(
        state = state,
        isBookmark = bookDetailViewModel.isBookmark.value,
        onBackClick = { handler(BookDetailContract.Event.OnBackClick) },
        onChangeBookmark = { handler(BookDetailContract.Event.OnChangeBookmark(it)) }
    )
}

@Composable
private fun BookDetailContents(
    state: BookDetailContract.State,
    isBookmark: Boolean,
    onBackClick: () -> Unit,
    onChangeBookmark: (Book) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopNavigationBackTitle(
                title = "도서 상세",
                onBackClick = onBackClick
            )
        },
        containerColor = Palette.Common100,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            when (state.viewState) {
                is BookDetailContract.State.BookDetailViewState.Loading -> {
                    BookDetailShimmerComponent()
                }
                is BookDetailContract.State.BookDetailViewState.Success -> {
                    BookDetailComponent(
                        book = state.viewState.book,
                        isBookmark = isBookmark,
                        onChangeBookmark = { onChangeBookmark(state.viewState.book) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookDetailContentsPreview() {
    BookDetailContents(
        state = BookDetailContract.State(
            viewState = BookDetailContract.State.BookDetailViewState.Success(
                book = Book(
                    id = "fdfsdf9000",
                    title = "테스트",
                    contents = "테스트컨텐츠",
                    url = "",
                    isbn = "fdfsdf",
                    datetime = "2025-08-15T00:00:00.000+09:00",
                    authors = listOf(),
                    publisher = "길잡이",
                    translators = listOf(),
                    price = 10000,
                    salePrice = 9000,
                    thumbnail = "",
                    status = "정상판매"
                )
            )
        ),
        isBookmark = false,
        onBackClick = { },
        onChangeBookmark = { }
    )
}