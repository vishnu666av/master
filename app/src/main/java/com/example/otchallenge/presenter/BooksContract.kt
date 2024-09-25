package com.example.otchallenge.presenter

import com.example.otchallenge.model.Book

interface BooksContract {
    interface View {
        fun showProgress()
        fun hideProgress()
        fun displayBooks(books: List<Book>)
        fun showError(message: String)
    }

    interface Presenter {
        fun attach(view: View)
        fun detach()
        fun fetchBooks()
    }
}