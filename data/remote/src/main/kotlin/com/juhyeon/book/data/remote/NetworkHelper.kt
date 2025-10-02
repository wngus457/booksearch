package com.juhyeon.book.data.remote

import com.juhyeon.book.data.repository.ResultWrapper
import com.juhyeon.book.shared.util.common.LogHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import kotlin.stackTraceToString

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    logHelper: LogHelper,
    apiCall: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    ResultWrapper.GenericError(
                        message = throwable.message ?: "",
                        exception = throwable
                    )
                }
                else -> {
                    logHelper.log(throwable.stackTraceToString())
                    ResultWrapper.GenericError(
                        message = "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요.",
                        exception = null
                    )
                }
            }
        }
    }
}