package com.example.otchallenge.ui.main

import com.example.otchallenge.data.models.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class BookListView
    @Inject
    constructor() : BookListContract.BookListView {
        private val _uiState = MutableStateFlow<ListResultUiState>(ListResultUiState.Loading)

        override fun onLoadBooks(books: List<Book>) {
            _uiState.update { ListResultUiState.Success(books) }
        }

        override val uiState: StateFlow<ListResultUiState> = _uiState.asStateFlow()

        override fun onError(e: Throwable) {
            _uiState.update { ListResultUiState.ErrorOccurred }
        }
    }
