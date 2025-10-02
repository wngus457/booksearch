package com.juhyeon.book.domain.feature.book.bookmark

import com.juhyeon.book.domain.FlowNoParamUseCase
import com.juhyeon.book.domain.Result
import com.juhyeon.book.domain.annotaion.DefaultDispatcher
import com.juhyeon.book.domain.feature.book.BookRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class GetAllBookmarkKeysUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher
) : FlowNoParamUseCase<List<String>>(dispatcher) {

    override fun execute(): Flow<Result<List<String>>> {
        return bookRepository.getAllBookmarkKeys().take(1)
    }
}