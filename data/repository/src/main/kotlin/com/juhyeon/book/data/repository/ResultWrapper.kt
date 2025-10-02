package com.juhyeon.book.data.repository

import com.juhyeon.book.domain.Result
import java.io.IOException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(
        val message: String,
        val exception: Exception?
    ) : ResultWrapper<Nothing>()
    data object NetworkError : ResultWrapper<Nothing>()
}

internal fun <T, D> ResultWrapper<T>.toDomain(onMapSuccess: (T) -> D) = when (this) {
    is ResultWrapper.Success -> Result.Success(onMapSuccess(value))
    is ResultWrapper.GenericError -> Result.Error(
        message = message,
        exception = exception ?: Exception()
    )
    is ResultWrapper.NetworkError -> Result.Error(
        message = "Network Error",
        exception = IOException()
    )
}