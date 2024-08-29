package com.example.otchallenge.domain.utils

import com.example.otchallenge.domain.errors.AppError
import retrofit2.Response

object NetworkUtils {
    fun <T> Response<T>.toResult(): Result<T> = try {
        if (isSuccessful) {
            body()?.let {
                Result.success(it)
            } ?: Result.failure(AppError.Http(message = "Empty body", httpCode = code()))
        } else {
            Result.failure(
                AppError.Http(
                    message = message(),
                    httpCode = code()
                )
            )
        }
    } catch (exception: Exception) {
        Result.failure(AppError.Network(exception.message, cause = exception))
    }
}
