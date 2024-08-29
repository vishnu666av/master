package com.example.otchallenge.ui.booklist.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.otchallenge.domain.models.BookList

@Composable
fun BookListComponent(
    bookList: BookList,
) {
    LazyColumn {
        items(bookList.books, key = { it.primaryIsbn10 }) {
            Text(text = it.title)
        }
    }
}
