package com.juhyeon.book.domain.feature.book.bookmark

import com.juhyeon.book.domain.FlowUseCase
import com.juhyeon.book.domain.Result
import com.juhyeon.book.domain.annotaion.DefaultDispatcher
import com.juhyeon.book.domain.feature.book.Book
import com.juhyeon.book.domain.feature.book.BookRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class GetAllBookmarksUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<String, List<Book>>(dispatcher) {

    override fun execute(parameters: String): Flow<Result<List<Book>>> {
        return bookRepository.getAllBookmarks(parameters).take(1)
    }
}