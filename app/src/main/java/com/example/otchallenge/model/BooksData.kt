package com.example.otchallenge.model

data class BooksData(
    val isLoading: Boolean = false,
    val shouldLoadMore: Boolean = false,
    val books: List<BookModel> = emptyList(),
    val showError: Boolean = false,
    val errorMessage: String? = null,
)
