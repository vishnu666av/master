package com.example.otchallenge.ui.booklist

import com.example.otchallenge.domain.models.BookList

data class BookListState(
    val bookListResult: Result<BookList>? = null
)
