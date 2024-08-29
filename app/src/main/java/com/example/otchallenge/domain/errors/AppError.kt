package com.example.otchallenge.domain.errors

import androidx.annotation.StringRes
import com.example.otchallenge.R

sealed class AppError(
    override val message: String?,
    override val cause: Throwable? = null

) : Throwable(
    message = message,
    cause = cause,
) {

    data class Http(
        override val message: String?,
        override val cause: Throwable? = null,
        val httpCode: Int,
    ) : AppError(
        message = message,
        cause = cause
    )

    data class Network(
        override val message: String?,
        override val cause: Throwable? = null,
    ) : AppError(
        message = message,
        cause = cause
    )
}

@StringRes fun AppError.getPresentationMessage(): Int = when(this) {
    is AppError.Http -> R.string.http_error_message
    is AppError.Network -> R.string.network_error_message
}

