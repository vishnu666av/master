package com.example.otchallenge.domain.utils

import com.example.otchallenge.domain.errors.AppError
import retrofit2.Response


inline fun <T> secureNetworkRequest(networkRequest: () -> Response<T>): Result<T> = try {
    val response = networkRequest()
    if (response.isSuccessful) {
        response.body()?.let {
            Result.success(it)
        } ?: Result.failure(AppError.Http(message = "Empty body", httpCode = response.code()))
    } else {
        Result.failure(
            AppError.Http(
                message = response.message(),
                httpCode = response.code()
            )
        )
    }
} catch (exception: Exception) {
    Result.failure(AppError.Network(exception.message, cause = exception))
}

