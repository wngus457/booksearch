package com.juhyeon.book.feature.search

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.juhyeon.book.domain.feature.book.Book
import com.juhyeon.book.domain.feature.book.bookmark.DeleteBookmarkUseCase
import com.juhyeon.book.domain.feature.book.bookmark.GetAllBookmarkKeysUseCase
import com.juhyeon.book.domain.feature.book.bookmark.InsertBookmarkUseCase
import com.juhyeon.book.domain.feature.book.list.GetBookListParam
import com.juhyeon.book.domain.feature.book.list.GetBookListUseCase
import com.juhyeon.book.domain.onError
import com.juhyeon.book.domain.onSuccess
import com.juhyeon.book.shared.core.mvi.BaseViewModel
import com.juhyeon.book.shared.navigation.BookDetail
import com.juhyeon.book.shared.ui.system.sort.SortSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getBookListUseCase: GetBookListUseCase,
    private val getAllBookmarkKeysUseCase: GetAllBookmarkKeysUseCase,
    private val insertBookmarkUseCase: InsertBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase
) : BaseViewModel<SearchContract.Event, SearchContract.State, SearchContract.Effect>() {

    private val _searchData = MutableStateFlow(SearchContract.State.SearchData())
    val searchData = _searchData.asStateFlow()
    val bookmarkKeys = mutableStateListOf<String>()

    override fun initState() = SearchContract.State(
        viewState = SearchContract.State.SearchViewState.Init
    )

    override fun handleEvent(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.OnSearchBookList -> onSearchKeyword(event.keyword)
            is SearchContract.Event.OnChangeSort -> changeSort(event.sort)
            is SearchContract.Event.OnChangePage -> changePage()
            is SearchContract.Event.OnChangeBookmark -> changeBookmark(event.isBookmark, event.book)
            is SearchContract.Event.OnBookClick -> navigateToBookDetail(event.book)
            is SearchContract.Event.OnBookmarkRefresh -> getAllBookmarkKeys()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getSearchBookList() {
        searchData.flatMapConcat {
            getBookListUseCase(
                GetBookListParam(
                    query = it.keyword,
                    sort = it.sort.key,
                    page = it.page
                )
            )
        }
            .onSuccess { book ->
                if (book.documents.isEmpty()) {
                    reducer.setState {
                        copy(viewState = SearchContract.State.SearchViewState.Empty)
                    }
                } else {
                    reducer.setState {
                        copy(
                            viewState = SearchContract.State.SearchViewState.Success(
                                meta = book.meta,
                                books = viewState.mergeBookList(book.documents)
                            )
                        )
                    }
                }
            }
            .onError { reducer.setState { copy(viewState = SearchContract.State.SearchViewState.Error(message = it.message ?: "")) } }
            .launchIn(viewModelScope)
    }

    private fun changeSort(sort: SortSearch) {
        if (sort != _searchData.value.sort) {
            reducer.setState { copy(viewState = SearchContract.State.SearchViewState.Loading) }
            _searchData.value = _searchData.value.copy(
                sort = sort,
                page = 1
            )
            reducer.setEffect(SearchContract.Effect.ScrollToTop)
        }
    }

    private fun changePage() {
        _searchData.value = _searchData.value.copy(
            page = searchData.value.page + 1
        )
    }

    private fun onSearchKeyword(localKeyword: String) {
        if (localKeyword != _searchData.value.keyword) {
            reducer.setState { copy(viewState = SearchContract.State.SearchViewState.Loading) }
            _searchData.value = _searchData.value.copy(
                keyword = localKeyword,
                page = 1
            )
            reducer.setEffect(SearchContract.Effect.ScrollToTop)
        }
        if (reducer.stateFlow.value.viewState is SearchContract.State.SearchViewState.Init) {
            getSearchBookList()
        }
    }

    private fun SearchContract.State.SearchViewState.mergeBookList(newSearchBookList: List<Book>): List<Book> {
        if (this is SearchContract.State.SearchViewState.Success) {
            return books + newSearchBookList
        }
        return newSearchBookList.distinct()
    }

    private fun changeBookmark(isBookmark: Boolean, book: Book) {
        if (isBookmark) {
            insertBookmark(book)
        } else {
            deleteBookmark(book.id)
        }
    }

    private fun insertBookmark(book: Book) {
        insertBookmarkUseCase(book)
            .onSuccess {
                getAllBookmarkKeys()
            }
            .launchIn(viewModelScope)
    }

    private fun getAllBookmarkKeys() {
        getAllBookmarkKeysUseCase()
            .onSuccess {
                bookmarkKeys.clear()
                bookmarkKeys.addAll(it)
            }
            .launchIn(viewModelScope)
    }

    private fun deleteBookmark(id: String) {
        deleteBookmarkUseCase(id)
            .onSuccess {
                getAllBookmarkKeys()
            }
            .launchIn(viewModelScope)
    }

    private fun navigateToBookDetail(book: Book) {
        val bookDetail = BookDetail(
            id = book.id,
            title = book.title,
            contents = book.contents,
            url = book.url,
            isbn = book.isbn,
            datetime = book.datetime,
            authors = book.authors.joinToString(","),
            publisher = book.publisher,
            translators = book.translators.joinToString(","),
            price = book.price,
            salePrice = book.salePrice,
            thumbnail = book.thumbnail,
            status = book.status
        )
        reducer.setEffect(SearchContract.Effect.NavigateToDetail(bookDetail))
    }
}