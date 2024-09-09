package com.example.otchallenge.ui.main

import androidx.paging.PagingData
import com.example.otchallenge.data.database.BookEntity
import kotlinx.coroutines.flow.StateFlow

interface BookListContract {
    interface BookListView {
        val paging: StateFlow<PagingData<BookEntity>>
        var loading: StateFlow<Boolean>
        var isOnline: StateFlow<Boolean>
        var error: StateFlow<Boolean>

        fun onLoadBooks(books: PagingData<BookEntity>)

        fun onError(e: Throwable)

        fun showLoader()

        fun hideLoader()

        fun onNetworkStateChanged(isOnline: Boolean)
    }

    interface BookListPresenter {
        fun listenToNetworkState()

        suspend fun fetchBooks(page: Int)

        fun attachView(view: BookListView)

        fun detachView()
    }
}
