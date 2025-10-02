package com.juhyeon.book.domain.feature.book.list

import com.juhyeon.book.domain.FlowUseCase
import com.juhyeon.book.domain.Result
import com.juhyeon.book.domain.annotaion.DefaultDispatcher
import com.juhyeon.book.domain.feature.book.BookRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class GetBookListUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : FlowUseCase<GetBookListParam, BookList>(dispatcher) {

    override fun execute(parameters: GetBookListParam): Flow<Result<BookList>> {
        return bookRepository.getBookList(parameters).distinctUntilChanged()
    }
}