package com.example.otchallenge.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otchallenge.data.models.Book
import com.example.otchallenge.data.network.BookDataSource
import com.example.otchallenge.di.AppDispatchers
import com.example.otchallenge.di.Dispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BookListViewModel
    @Inject
    constructor(
        @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    ) : ViewModel() {
        val resultState: StateFlow<ListResultUiState> =
            BookDataSource()
                .fetchBooks()
                .map<List<Book>, ListResultUiState> {
                    ListResultUiState.Success(it)
                }.onStart { flowOf(ListResultUiState.Loading) }
                .catch {
                    it.printStackTrace()
                    emit(ListResultUiState.ErrorOccurred)
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = ListResultUiState.Loading,
                )
    }
