package com.example.otchallenge.ui.bookslist.presenter

import androidx.lifecycle.LiveData
import com.example.otchallenge.ui.bookslist.view.BookListView

interface IBooksListPresenter {

    val uiState: LiveData<BooksListUiState>

    fun onViewAttached(view: BookListView)

    fun getBooks()

    fun onViewDetached()
}