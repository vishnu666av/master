package com.example.otchallenge.model

import com.example.otchallenge.data.BookDto
import java.util.Date

sealed class BooksListDataState {

    data class Empty(val timestamp: Date) : BooksListDataState()

    data class FreshList(val items: List<BookDto>, val timestamp: Date) : BooksListDataState()

    data class StaleList(val items: List<BookDto>, val timestamp: Date) : BooksListDataState()

    data class Error(val message: String?, val timestamp: Date) : BooksListDataState()
}