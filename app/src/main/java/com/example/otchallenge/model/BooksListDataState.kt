package com.example.otchallenge.model

import com.example.otchallenge.data.BookDto

sealed class BooksListDataState {

    data class Empty(val timestamp: String) : BooksListDataState()

    data class FreshList(val items: List<BookDto>, val timestamp: String) : BooksListDataState()

    data class StaleList(val items: List<BookDto>, val timestamp: String) : BooksListDataState()

    data class Error(val message: String?, val timestamp: String) : BooksListDataState()
}