package com.example.otchallenge.booklist.uistate

sealed class LoadingState {
    // ** Delivers the loaded data to the UI layer
    data object Ready : LoadingState()

    // ** Simple loading state to be shown on the UI layer
    data object Loading : LoadingState()

    // ** Delivers the error to the UI layer
    data class Error(val throwable: Throwable? = null) : LoadingState()
}