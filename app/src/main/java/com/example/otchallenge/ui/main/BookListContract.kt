package com.example.otchallenge.ui.main

import com.example.otchallenge.data.models.Book
import kotlinx.coroutines.flow.StateFlow

interface BookListContract {
    interface BookListView {
        val uiState: StateFlow<ListResultUiState>

        fun onLoadBooks(books: List<Book>)

        fun onError(e: Throwable)
    }

    interface BookListPresenter {
        fun fetchBooks(page: Int)

        fun attachView(view: BookListView)

        fun detachView()
    }
}
