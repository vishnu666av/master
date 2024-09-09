package com.example.otchallenge.ui.main

import androidx.paging.PagingData
import com.example.otchallenge.data.models.Book

sealed interface ListResultUiState {
    data object Loading : ListResultUiState

    data object ErrorOccurred : ListResultUiState

    data object NoNetwork : ListResultUiState

    data class Success(
        val items: List<Book> = emptyList(),
        val pagedItems: PagingData<Book>,
    ) : ListResultUiState {
        fun isEmpty(): Boolean = items.isEmpty()
    }
}
