package com.example.otchallenge.domain

sealed class BaseResult<out T : Any?> {

    class Success<out T : Any?>(val value: T) : BaseResult<T>()

    object Empty : BaseResult<Nothing>()

    class Error(val cause: Throwable) : BaseResult<Nothing>()
}
