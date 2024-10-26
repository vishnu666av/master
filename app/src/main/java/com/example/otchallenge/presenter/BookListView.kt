package com.example.otchallenge.presenter

import com.example.otchallenge.model.Book

interface BookListView {

    fun onBooksFetched(books: List<Book>)
    fun showError(message: String)
}