package com.example.otchallenge.ui.booklist

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun BookListComponent(
    modifier: Modifier = Modifier,
    bookListViewModel: BookListViewModel  = viewModel()
) {
    val state by bookListViewModel.getState().collectAsState()
    state.bookListResult?.onSuccess {
        LazyColumn(
            modifier = modifier
        ) {
            items(it.books, key = { it.primaryIsbn10 }) {
                Text(text = it.title)
            }
        }
    }

}
