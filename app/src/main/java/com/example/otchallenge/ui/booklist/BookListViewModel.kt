package com.example.otchallenge.ui.booklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otchallenge.domain.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
) : ViewModel() {
    private val state = MutableStateFlow(BookListState())


    fun fetchBooks() {
        viewModelScope.launch {
            state.update { it.copy(bookListResult = null) }
            val result = booksRepository.fetchDefaultList()
            state.update {
                it.copy(bookListResult = result)
            }
        }
    }

    fun getState(): StateFlow<BookListState> = state.asStateFlow()
}
