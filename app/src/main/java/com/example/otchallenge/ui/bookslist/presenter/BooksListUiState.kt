package com.example.otchallenge.ui.bookslist.presenter

import com.example.otchallenge.model.BookDto

sealed class BooksListUiState {

    /**
     * this is the initial state of the ui before it attempts to do any meaningful operations.
     */
    data object Idle : BooksListUiState()

    data object Loading : BooksListUiState()

    data class Empty(val timestamp: String = "") : BooksListUiState()

    data class OfflineList(val items: List<BookDto>, val timestamp: String = "") :
        BooksListUiState()

    data class OnlineList(val items: List<BookDto>, val timestamp: String = "") : BooksListUiState()

    data object Error : BooksListUiState()
}