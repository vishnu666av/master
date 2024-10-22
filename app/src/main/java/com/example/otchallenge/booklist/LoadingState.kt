package com.example.otchallenge.booklist

sealed class LoadingState<out T> {
    // ** Delivers the loaded data to the UI layer
    data class Ready<T>(val content: T? = null) : LoadingState<T>()

    // ** Simple loading state to be shown on the UI layer
    data object Loading : LoadingState<Nothing>()

    // ** Delivers the error to the UI layer
    data class Error(val throwable: Throwable? = null) : LoadingState<Nothing>()
}