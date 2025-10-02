package com.juhyeon.book.domain.feature.book.bookmark

import com.juhyeon.book.domain.FlowUseCase
import com.juhyeon.book.domain.Result
import com.juhyeon.book.domain.annotaion.DefaultDispatcher
import com.juhyeon.book.domain.feature.book.Book
import com.juhyeon.book.domain.feature.book.BookRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class InsertBookmarkUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<Book, Unit>(dispatcher) {

    override fun execute(parameters: Book): Flow<Result<Unit>> = flow {
        emit(Result.Success(bookRepository.insertBookmark(parameters)))
    }
}