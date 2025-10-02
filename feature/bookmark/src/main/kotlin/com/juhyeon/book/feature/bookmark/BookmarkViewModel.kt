package com.juhyeon.book.feature.bookmark

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.viewModelScope
import com.juhyeon.book.domain.feature.book.Book
import com.juhyeon.book.domain.feature.book.bookmark.DeleteBookmarkUseCase
import com.juhyeon.book.domain.feature.book.bookmark.GetAllBookmarkKeysUseCase
import com.juhyeon.book.domain.feature.book.bookmark.GetAllBookmarksUseCase
import com.juhyeon.book.domain.onSuccess
import com.juhyeon.book.shared.core.mvi.BaseViewModel
import com.juhyeon.book.shared.navigation.BookDetail
import com.juhyeon.book.shared.ui.system.sort.FilterBookmark
import com.juhyeon.book.shared.ui.system.sort.SortBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getAllBookmarksUseCase: GetAllBookmarksUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val getAllBookmarkKeysUseCase: GetAllBookmarkKeysUseCase
) : BaseViewModel<BookmarkContract.Event, BookmarkContract.State, BookmarkContract.Effect>() {

    private val _searchBookmark = MutableStateFlow(BookmarkContract.State.SearchBookmark())
    val searchBookmark = _searchBookmark.asStateFlow()
    val bookmarkKeys = mutableStateListOf<String>()

    override fun initState() = BookmarkContract.State(viewState = BookmarkContract.State.BookmarkViewState.Loading)

    override fun handleEvent(event: BookmarkContract.Event) {
        when (event) {
            is BookmarkContract.Event.OnRefresh -> getAllBookmark()
            is BookmarkContract.Event.OnSearchBookList -> changeKeyword(event.keyword)
            is BookmarkContract.Event.OnDelectBookmark -> deleteBookmark(event.id)
            is BookmarkContract.Event.OnChangeSort -> changeSort(event.sort)
            is BookmarkContract.Event.OnChangeFilter -> changeFilter(event.filter)
            is BookmarkContract.Event.OnBookClick -> navigateToBookDetail(event.book)
        }
    }

    init {
        getAllBookmarkKeys()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getAllBookmark() {
        searchBookmark.flatMapConcat {
            getAllBookmarksUseCase(it.keyword)
        }
            .onSuccess { list ->
                if (list.isEmpty()) {
                    reducer.setState { copy(viewState = BookmarkContract.State.BookmarkViewState.Empty) }
                } else {
                    reducer.setState {
                        copy(
                            viewState = BookmarkContract.State.BookmarkViewState.Success(
                                data = list
                                    .filter { filterPrice(it.salePrice) }
                                    .sort(searchBookmark.value.sort)
                            )
                        )
                    }
                    reducer.setEffect(BookmarkContract.Effect.ScrollTop)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun deleteBookmark(id: String) {
        deleteBookmarkUseCase(id)
            .onSuccess { getAllBookmark() }
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

    private fun changeKeyword(localKeyword: String) {
        _searchBookmark.value = searchBookmark.value.copy(keyword = localKeyword)
    }

    private fun changeSort(sort: SortBookmark) {
        if (sort != searchBookmark.value.sort) {
            reducer.setState { copy(viewState = BookmarkContract.State.BookmarkViewState.Loading) }
            _searchBookmark.value = _searchBookmark.value.copy(sort = sort)
        }
    }

    private fun changeFilter(filter: FilterBookmark) {
        if (filter != searchBookmark.value.filter) {
            reducer.setState { copy(viewState = BookmarkContract.State.BookmarkViewState.Loading) }
            _searchBookmark.value = _searchBookmark.value.copy(filter = filter)
        }
    }

    private fun List<Book>.sort(sortBookmark: SortBookmark): List<Book> {
        return when (sortBookmark) {
            SortBookmark.ASCENDING -> this.sortedBy { it.title }
            SortBookmark.DESCENDING -> this.sortedByDescending { it.title }
        }
    }

    private fun filterPrice(price: Int): Boolean {
        return when (searchBookmark.value.filter) {
            FilterBookmark.ALL -> 0 <= price
            FilterBookmark.WON10000 -> 0 <= price && price < 10000
            FilterBookmark.WON20000 -> 10000 <= price && price < 20000
            FilterBookmark.WON30000 -> 20000 <= price && price < 30000
            FilterBookmark.WON40000 -> 30000 <= price && price < 40000
            FilterBookmark.MAX -> 40000 <= price
        }
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
        reducer.setEffect(BookmarkContract.Effect.NavigateToDetail(bookDetail))
    }
}