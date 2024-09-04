package com.example.otchallenge.presentation.bookList

import com.example.otchallenge.presentation.base.BaseContract
import com.example.otchallenge.presentation.bookList.model.BookItemModel

interface BookListContract {
    interface View : BaseContract.View {
        fun showBooks(books: List<BookItemModel>)
    }

    interface Presenter : BaseContract.Presenter<View> {
        fun loadBooks()
    }
}