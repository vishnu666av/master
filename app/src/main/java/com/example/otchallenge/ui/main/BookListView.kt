package com.example.otchallenge.ui.main

import androidx.paging.PagingData
import com.example.otchallenge.data.database.BookEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class BookListView
    @Inject
    constructor() : BookListContract.BookListView {
        private val _loading = MutableStateFlow(false)
        private val _error = MutableStateFlow(false)
        private val _isOnline = MutableStateFlow(true)
        private val _data =
            MutableStateFlow<PagingData<BookEntity>>(
                PagingData.empty(),
            )

        override fun onLoadBooks(books: PagingData<BookEntity>) {
            _data.update { books }
        }

        override val paging: StateFlow<PagingData<BookEntity>> = _data.asStateFlow()
        override var loading: StateFlow<Boolean> = _loading.asStateFlow()
        override var error: StateFlow<Boolean> = _error.asStateFlow()
    override var isOnline: StateFlow<Boolean> = _isOnline.asStateFlow()
        override fun onError(e: Throwable) {
            _error.update { true }
        }

        override fun showLoader() {
            _loading.update { true }
        }

        override fun hideLoader() {
            _loading.update { false }
        }

        override fun onNetworkStateChanged(isOnline: Boolean) {
            _isOnline.update { isOnline }
        }
    }
