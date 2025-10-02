package com.juhyeon.book.feature.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.juhyeon.book.domain.feature.book.Book
import com.juhyeon.book.domain.feature.book.bookmark.DeleteBookmarkUseCase
import com.juhyeon.book.domain.feature.book.bookmark.GetBookmarkUseCase
import com.juhyeon.book.domain.feature.book.bookmark.InsertBookmarkUseCase
import com.juhyeon.book.domain.onSuccess
import com.juhyeon.book.shared.core.mvi.BaseViewModel
import com.juhyeon.book.shared.navigation.BookDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val getBookmarkUseCase: GetBookmarkUseCase,
    private val deleteBookmarkUseCase: DeleteBookmarkUseCase,
    private val insertBookmarkUseCase: InsertBookmarkUseCase
) : BaseViewModel<BookDetailContract.Event, BookDetailContract.State, BookDetailContract.Effect>() {

    private val bookDetail = handle.toRoute<BookDetail>()
    val isBookmark = mutableStateOf(false)

    override fun initState() = BookDetailContract.State(
        viewState = BookDetailContract.State.BookDetailViewState.Loading
    )

    override fun handleEvent(event: BookDetailContract.Event) {
        when (event) {
            is BookDetailContract.Event.OnBackClick -> reducer.setEffect(BookDetailContract.Effect.NavigateToBack)
            is BookDetailContract.Event.OnChangeBookmark -> changeBookmark(event.book)
        }
    }

    init {
        val book = Book(
            id = bookDetail.id,
            title = bookDetail.title,
            contents = bookDetail.contents,
            url = bookDetail.url,
            isbn = bookDetail.isbn,
            datetime = bookDetail.datetime,
            authors = bookDetail.authors.split(",").map { it },
            publisher = bookDetail.publisher,
            translators = bookDetail.translators.split(",").map { it },
            price = bookDetail.price,
            salePrice = bookDetail.salePrice,
            thumbnail = bookDetail.thumbnail,
            status = bookDetail.status
        )
        getBookmarkUseCase(bookDetail.id)
            .onSuccess {
                isBookmark.value = it != null
                reducer.setState { copy(viewState = BookDetailContract.State.BookDetailViewState.Success(book)) }
            }
            .launchIn(viewModelScope)
    }

    private fun changeBookmark(book: Book) {
        if (isBookmark.value) {
            deleteBookmark()
        } else {
            insertBookmark(book)
        }
    }

    private fun deleteBookmark() {
        deleteBookmarkUseCase(bookDetail.id)
            .onSuccess { isBookmark.value = false }
            .launchIn(viewModelScope)
    }

    private fun insertBookmark(book: Book) {
        insertBookmarkUseCase(book)
            .onSuccess { isBookmark.value = true }
            .launchIn(viewModelScope)
    }
}