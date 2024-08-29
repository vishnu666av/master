package com.example.otchallenge.ui.booklist

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.otchallenge.R
import com.example.otchallenge.domain.errors.AppError
import com.example.otchallenge.domain.errors.getPresentationMessage
import com.example.otchallenge.ui.booklist.components.BookListComponent
import com.example.otchallenge.ui.corecomponents.RecoverableError

@Composable
fun BookListContent(
    modifier: Modifier = Modifier,
    bookListViewModel: BookListViewModel = viewModel()
) {
    val state by bookListViewModel.getState().collectAsState()
    val result = state.bookListResult
    Box(modifier = modifier.then(Modifier.fillMaxSize())) {
        if (result == null) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            result.onSuccess { list ->
                BookListComponent(bookList = list)
            }
            result.onFailure {
                Log.d("BookListContent", it.message, it)
                RecoverableError(
                    title = { Text(text = stringResource(R.string.book_list_error_title)) },
                    message = {
                        if (it is AppError) {
                            Text(text = stringResource(id = it.getPresentationMessage()))
                        } else {
                            Text(text = it.localizedMessage ?: "")
                        }
                    },
                    onRetry = bookListViewModel::fetchBooks,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
    LaunchedEffect(Unit) {
        bookListViewModel.fetchBooks()
    }

}
