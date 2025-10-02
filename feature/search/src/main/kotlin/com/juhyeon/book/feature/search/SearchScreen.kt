package com.juhyeon.book.feature.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import com.juhyeon.book.domain.feature.book.Book
import com.juhyeon.book.domain.feature.book.list.BookList
import com.juhyeon.book.feature.search.component.SearchEmptyComponent
import com.juhyeon.book.feature.search.component.SearchSortComponent
import com.juhyeon.book.feature.search.component.searchShimmerComponent
import com.juhyeon.book.shared.ui.extension.addFocusCleaner
import com.juhyeon.book.shared.ui.extension.reachedBottom
import com.juhyeon.book.shared.ui.system.book.BookItemComponent
import com.juhyeon.book.shared.ui.system.lifecycle.OnLifecycleEvent
import com.juhyeon.book.shared.ui.system.navigation.top.TopNavigationCenterTitle
import com.juhyeon.book.shared.ui.system.search.SearchBar
import com.juhyeon.book.shared.ui.system.sort.SortSearch
import com.juhyeon.book.shared.ui.system.theme.Palette
import com.juhyeon.book.shared.ui.system.theme.bold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val state = searchViewModel.stateFlow.collectAsState().value
    val handler = searchViewModel.eventHandler
    var focusState by rememberSaveable { mutableStateOf(true) }
    val focusManager = LocalFocusManager.current
    val lazyState = rememberLazyListState()
    val reachedBottom by remember {
        derivedStateOf {
            lazyState.reachedBottom()
        }
    }
    val localKeyword = remember { mutableStateOf("") }
    val searchDataCollect = searchViewModel.searchData.collectAsState().value
    val scope = rememberCoroutineScope()

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> handler(SearchContract.Event.OnBookmarkRefresh)
            else -> {}
        }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom && state.viewState is SearchContract.State.SearchViewState.Success) {
            if (state.viewState.books.isNotEmpty() && state.viewState.meta.isEnd.not()) {
                handler(SearchContract.Event.OnChangePage)
            }
        }
    }

    LaunchedEffect(true) {
        searchViewModel.effectFlow.collect { effect ->
            when (effect) {
                is SearchContract.Effect.NavigateToDetail -> navController.navigate(effect.bookDetail)
                is SearchContract.Effect.ScrollToTop -> {
                    scope.launch {
                        delay(100)
                        lazyState.scrollToItem(0)
                    }
                }
            }
        }
    }

    SearchContents(
        state = state,
        lazyState = lazyState,
        bookmarkKeys = searchViewModel.bookmarkKeys,
        keyword = localKeyword.value,
        focusState = focusState,
        selectedSort = searchDataCollect.sort,
        focusManager = focusManager,
        onChangeKeyword = { localKeyword.value = it },
        onChangeFocus = { focusState = it },
        onSearchBookList = {
            handler(SearchContract.Event.OnSearchBookList(localKeyword.value))
            focusManager.clearFocus()
        },
        onChangeSort = { handler(SearchContract.Event.OnChangeSort(it)) },
        onChangeBookmark = { isBookmark, item -> handler(SearchContract.Event.OnChangeBookmark(isBookmark, item)) },
        onBookClick = { handler(SearchContract.Event.OnBookClick(it)) }
    )
}

@Composable
private fun SearchContents(
    state: SearchContract.State,
    lazyState: LazyListState,
    bookmarkKeys: List<String>,
    keyword: String,
    focusState: Boolean,
    selectedSort: SortSearch,
    focusManager: FocusManager,
    onChangeKeyword: (String) -> Unit,
    onChangeFocus: (Boolean) -> Unit,
    onSearchBookList: () -> Unit,
    onChangeSort: (SortSearch) -> Unit,
    onChangeBookmark: (Boolean, Book) -> Unit,
    onBookClick: (Book) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager),
        topBar = { TopNavigationCenterTitle(title = "검색") },
        containerColor = Palette.Common100,
        contentWindowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Column(modifier = Modifier.padding(it)) {
            SearchBar(
                text = keyword,
                focusState = focusState,
                onFocused = { focus -> onChangeFocus(focus.isFocused) },
                onChangeText = onChangeKeyword,
                onSearchClick = onSearchBookList
            )

            SearchSortComponent(
                selectedSort = selectedSort,
                onChangeSort = onChangeSort
            )

            LazyColumn(
                state = lazyState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Palette.Neutral70),
                contentPadding = PaddingValues(16.dp),
                userScrollEnabled = state.viewState is SearchContract.State.SearchViewState.Success,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (state.viewState) {
                    is SearchContract.State.SearchViewState.Success -> {
                        items(
                            items = state.viewState.books,
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
                    is SearchContract.State.SearchViewState.Error -> {
                        item {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = state.viewState.message,
                                style = MaterialTheme.typography.bold(20),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    is SearchContract.State.SearchViewState.Loading -> {
                        searchShimmerComponent()
                    }
                    is SearchContract.State.SearchViewState.Init -> {
                        item {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = "검색창을 통해 찾고싶은 책을 입력하세요!",
                                style = MaterialTheme.typography.bold(18),
                                textAlign = TextAlign.Center,
                                color = Palette.Neutral30
                            )
                        }
                    }
                    is SearchContract.State.SearchViewState.Empty -> {
                        item {
                            SearchEmptyComponent()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchContentsPreview() {
    SearchContents(
        state = SearchContract.State(
            viewState = SearchContract.State.SearchViewState.Success(
                meta = BookList.BookMetaData(
                    isEnd = true,
                    pageableCount = 20,
                    totalCount = 100
                ),
                books = listOf(
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
                ),
            )
        ),
        lazyState = rememberLazyListState(),
        bookmarkKeys = listOf(),
        keyword = "테스트",
        focusState = false,
        selectedSort = SortSearch.ACCURACY,
        focusManager = LocalFocusManager.current,
        onChangeKeyword = { },
        onChangeFocus = { },
        onSearchBookList = { },
        onChangeSort = { },
        onChangeBookmark = { _, _ -> },
        onBookClick = { }
    )
}