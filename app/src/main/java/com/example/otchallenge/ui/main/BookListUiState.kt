package com.example.otchallenge.ui.main

import com.example.otchallenge.data.models.Book

sealed interface ListResultUiState {
    data object Loading : ListResultUiState

    data object ErrorOccurred : ListResultUiState

    data class Success(
        val items: List<Book> = emptyList(),
    ) : ListResultUiState {
        fun isEmpty(): Boolean = items.isEmpty()
    }
}
