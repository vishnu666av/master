package com.example.otchallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otchallenge.model.Book
import com.example.otchallenge.model.BooksListDataState
import com.example.otchallenge.model.BooksListPresenter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


class BooksListViewModel @Inject constructor(presenter: BooksListPresenter) : ViewModel() {

    private val _uiState = MutableStateFlow<BooksListUiState>(BooksListUiState.Idle)
    val uiState: StateFlow<BooksListUiState> get() = _uiState

    init {
        viewModelScope.launch {
            _uiState.update { BooksListUiState.Loading }

            _uiState.update {
                when (val result = presenter.getFictions()) {
                    is BooksListDataState.Empty ->
                        BooksListUiState.Empty(formatDate(result.timestamp))

                    is BooksListDataState.Error -> BooksListUiState.Error

                    is BooksListDataState.FreshList -> BooksListUiState.OnlineList(
                        result.items.map { Book.fromBookDto(it) },
                        formatDate(result.timestamp)
                    )

                    is BooksListDataState.StaleList -> BooksListUiState.OfflineList(
                        result.items.map { Book.fromBookDto(it) },
                        formatDate(result.timestamp)
                    )
                }
            }
        }
    }

    // todo: timestamps come as Date() from presenter, and need formatting here.
    private fun formatDate(date: Date): String {
        return date.toString()
    }
}