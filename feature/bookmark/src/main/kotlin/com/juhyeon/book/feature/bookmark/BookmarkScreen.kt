package com.juhyeon.book.feature.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.juhyeon.book.domain.feature.book.Book
import com.juhyeon.book.feature.bookmark.component.BookmarkEmptyComponent
import com.juhyeon.book.feature.bookmark.component.BookmarkSortComponent
import com.juhyeon.book.feature.bookmark.component.bookmarkShimmerComponent
import com.juhyeon.book.shared.ui.system.book.BookItemComponent
import com.juhyeon.book.shared.ui.system.lifecycle.OnLifecycleEvent
import com.juhyeon.book.shared.ui.system.navigation.top.TopNavigationCenterTitle
import com.juhyeon.book.shared.ui.system.search.SearchBar
import com.juhyeon.book.shared.ui.system.sort.FilterBookmark
import com.juhyeon.book.shared.ui.system.sort.SortBookmark
import com.juhyeon.book.shared.ui.system.theme.Palette

@Composable
fun BookmarkScreen(
    navController: NavHostController,
    bookmarkViewModel: BookmarkViewModel = hiltViewModel()
) {
    val state = bookmarkViewModel.stateFlow.collectAsState().value
    val handler = bookmarkViewModel.eventHandler
    val localKeyword = remember { mutableStateOf("") }
    var focusState by rememberSaveable { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current
    val lazyState = rememberLazyListState()
    val searchBookmark = bookmarkViewModel.searchBookmark.collectAsState().value

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> handler(BookmarkContract.Event.OnRefresh)
            else -> {}
        }
    }

    LaunchedEffect(true) {
        bookmarkViewModel.effectFlow.collect { effect ->
            when (effect) {
                is BookmarkContract.Effect.ScrollTop -> lazyState.animateScrollToItem(0)
                is BookmarkContract.Effect.NavigateToDetail -> navController.navigate(effect.bookDetail)
            }
        }
    }

    BookmarkContents(
        state = state,
        lazyState = lazyState,
        keyword = localKeyword.value,
        focusState = focusState,
        selectedSort = searchBookmark.sort,
        selectedFilter = searchBookmark.filter,
        bookmarkKeys = bookmarkViewModel.bookmarkKeys,
        onChangeFocus = { focusState = it },
        onChangeKeyword = {
            localKeyword.value = it
            if (it.isEmpty()) handler(BookmarkContract.Event.OnSearchBookList(localKeyword.value))
        },
        onSearchBookList = {
            handler(BookmarkContract.Event.OnSearchBookList(localKeyword.value))
            focusManager.clearFocus()
        },
        onChangeSort = { handler(BookmarkContract.Event.OnChangeSort(it)) },
        onChangeFilter = { handler(BookmarkContract.Event.OnChangeFilter(it)) },
        onChangeBookmark = { _, item -> handler(BookmarkContract.Event.OnDelectBookmark(item.id)) },
        onBookClick = { handler(BookmarkContract.Event.OnBookClick(it)) }
    )
}

@Composable
private fun BookmarkContents(
    state: BookmarkContract.State,
    lazyState: LazyListState,
    keyword: String,
    focusState: Boolean,
    selectedSort: SortBookmark,
    selectedFilter: FilterBookmark,
    bookmarkKeys: List<String>,
    onChangeFocus: (Boolean) -> Unit,
    onChangeKeyword: (String) -> Unit,
    onSearchBookList: () -> Unit,
    onChangeSort: (SortBookmark) -> Unit,
    onChangeFilter: (FilterBookmark) -> Unit,
    onChangeBookmark: (Boolean, Book) -> Unit,
    onBookClick: (Book) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopNavigationCenterTitle("즐겨찾기") },
        containerColor = Palette.Common100,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            SearchBar(
                text = keyword,
                focusState = focusState,
                onFocused = { focus -> onChangeFocus(focus.isFocused) },
                onChangeText = onChangeKeyword,
                onSearchClick = onSearchBookList
            )

            BookmarkSortComponent(
                selectedSort = selectedSort,
                selectedFilter = selectedFilter,
                onChangeSort = onChangeSort,
                onChangeFilter = onChangeFilter
            )

            LazyColumn(
                state = lazyState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Palette.Neutral70),
                contentPadding = PaddingValues(16.dp),
                userScrollEnabled = state.viewState is BookmarkContract.State.BookmarkViewState.Success,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (state.viewState) {
                    is BookmarkContract.State.BookmarkViewState.Success -> {
                        items(
                            items = state.viewState.data,
                            key = { item -> item.id }
                        ) { item ->
                            BookItemComponent(
                                item = item,
                                isBookmark = bookmarkKeys.firstOrNull { key -> key == item.id } != null,
                                onClick = { onBookClick(item) },
                                onChangeBookmark = onChangeBookmark
                            )
                        }
                    }
                    is BookmarkContract.State.BookmarkViewState.Loading -> {
                        bookmarkShimmerComponent()
                    }
                    is BookmarkContract.State.BookmarkViewState.Empty -> {
                        item {
                            BookmarkEmptyComponent()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BookmarkContentsPreview() {
    BookmarkContents(
        state = BookmarkContract.State(
            viewState = BookmarkContract.State.BookmarkViewState.Success(
                listOf(
                    Book(
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
            )
        ),
        lazyState = rememberLazyListState(),
        keyword = "",
        focusState = false,
        bookmarkKeys = listOf(),
        selectedSort = SortBookmark.ASCENDING,
        selectedFilter = FilterBookmark.ALL,
        onChangeFocus = { },
        onChangeKeyword = { },
        onSearchBookList = { },
        onChangeSort = { },
        onChangeFilter = { },
        onChangeBookmark = { _, _ -> },
        onBookClick = { }
    )
}