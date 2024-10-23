package com.example.otchallenge.booklist.uistate

import java.time.LocalDateTime

data class BooksContentUIState(
    val books: List<BookUIState>,
    val listName: String,
    val lastModified: LocalDateTime,

) {
    companion object {
        val EMPTY = BooksContentUIState(
            books = emptyList(),
            listName = "",
            lastModified = LocalDateTime.now(),
        )
    }
}
