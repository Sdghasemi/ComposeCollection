package com.hirno.compose.collection.network.response

import okio.IOException

/**
 * A simplified version of the network response received from Retrofit library
 *
 * @param SuccessModel The model of the called REST endpoint created after a successful result
 * @param ErrorModel The model of the error response after a REST endpoint call
 */
sealed class NetworkResponse<out SuccessModel : Any, out ErrorModel : Any> {
    /**
     * Success response with body
     */
    data class Success<T : Any>(val body: T) : NetworkResponse<T, Nothing>()

    /**
     * Failure response with body
     */
    data class ApiError<U : Any>(val body: U, val code: Int) : NetworkResponse<Nothing, U>()

    /**
     * Network error
     */
    data class NetworkError(val error: IOException) : NetworkResponse<Nothing, Nothing>()

    /**
     * For example, json parsing error
     */
    data class UnknownError(val error: Throwable?) : NetworkResponse<Nothing, Nothing>()
}