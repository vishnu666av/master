package com.example.otchallenge.ui.booklist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.otchallenge.domain.models.BookList

@Composable
fun BookListComponent(
    bookList: BookList,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(bookList.books, key = { it.primaryIsbn10 }) {
            BookItemComponent(book = it)
        }
    }
}
