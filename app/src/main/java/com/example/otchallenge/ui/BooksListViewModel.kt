package com.example.otchallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otchallenge.model.Book
import com.example.otchallenge.model.BooksListDataState
import com.example.otchallenge.model.BooksListPresenter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class BooksListViewModel @Inject constructor(private val presenter: BooksListPresenter) :
    ViewModel() {

    private val _uiState = MutableStateFlow<BooksListUiState>(BooksListUiState.Idle)
    val uiState: StateFlow<BooksListUiState> get() = _uiState

    init {
        getBooks()
    }

    fun getBooks() = viewModelScope.launch {
        viewModelScope.launch {
            _uiState.update { BooksListUiState.Loading }

            // delay added to enhance visual appeal of state transitions. feel free to remove.
            delay(2500)

            _uiState.update {
                when (val result = presenter.getBooks()) {
                    is BooksListDataState.Empty ->
                        BooksListUiState.Empty(result.timestamp)

                    is BooksListDataState.Error -> BooksListUiState.Error

                    is BooksListDataState.FreshList -> BooksListUiState.OnlineList(
                        result.items.map { Book.fromBookDto(it) },
                        result.timestamp
                    )

                    is BooksListDataState.StaleList -> BooksListUiState.OfflineList(
                        result.items.map { Book.fromBookDto(it) },
                        result.timestamp
                    )
                }
            }
        }
    }
}