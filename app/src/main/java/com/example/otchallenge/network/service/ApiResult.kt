package com.example.otchallenge.network.service

import retrofit2.Response

/**
 * Represents the result of a network operation, encapsulating both successful outcomes
 * with data of type [T] and failures with an error message.
 */
sealed class ApiResult<T> {

    /**
     * Companion object to facilitate the creation of [ApiResult] instances based on
     * Retrofit [Response] objects.
     */
    companion object {
        /**
         * Creates an [ApiResult] instance from a Retrofit [Response].
         * If the response is successful, wraps the body in [OnSuccess].
         * If the response is not successful, wraps the error message in [OnFailure].
         *
         * @param response The Retrofit [Response] from a network call.
         * @return An [ApiResult] representing the outcome of the network call.
         */
        fun <T> create(response: Response<T>): ApiResult<T> {
            return if (response.isSuccessful) {
                val body = response.body()
                Success(body)
            } else {
                val errorBody = response.errorBody()?.string()
                val isErrorBodyNotEmpty = !errorBody.isNullOrEmpty()
                val errorMessage = if (isErrorBodyNotEmpty) errorBody else response.message()
                Error(errorMessage ?: "An unexpected error occurred. Please try again.")
            }
        }
    }

    /**
     * Represents a successful outcome of a network operation, containing the resulting data.
     *
     * @param data The data resulting from the successful network operation, which can be null.
     */
    data class Success<T>(val data: T?) : ApiResult<T>()

    /**
     * Represents a failure outcome of a network operation, containing an error message.
     *
     * @param message The error message describing the failure.
     */
    data class Error<T>(val message: String) : ApiResult<T>()
}

