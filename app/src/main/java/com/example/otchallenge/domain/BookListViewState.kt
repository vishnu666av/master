package com.example.otchallenge.domain

/**
 * UiState for the book list screen.
 */
data class BookListViewState(
    val items: List<BookListContent> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val userMessage: String? = null
)