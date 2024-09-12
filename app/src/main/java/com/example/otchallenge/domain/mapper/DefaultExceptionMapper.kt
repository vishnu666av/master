package com.example.otchallenge.domain.mapper


import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

class DefaultExceptionMapper : ExceptionMapper {
    override fun map(exception: Throwable): String {
        return when (exception) {
            is HttpException -> handleHttpException(exception)
            is IOException -> "Network error. Please check your internet connection."
            is TimeoutException -> "Request timed out. Please try again."
            is JsonParseException -> "Data parsing error. Please try again later."
            else -> "An unexpected error occurred. Please try again."
        }
    }

    private fun handleHttpException(exception: HttpException): String {
        return when (exception.code()) {
            400 -> "Bad request. Please try again."
            401 -> "Unauthorized access. Please log in again."
            403 -> "Forbidden. You do not have permission to perform this action."
            404 -> "Resource not found."
            500 -> "Internal server error. Please try again later."
            else -> "Unexpected server error. Please try again."
        }
    }
}
