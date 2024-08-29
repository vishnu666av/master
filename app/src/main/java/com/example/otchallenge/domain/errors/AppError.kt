package com.example.otchallenge.domain.errors

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

