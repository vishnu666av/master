package com.example.otchallenge.presenter

import com.example.otchallenge.model.Book

interface BookDetailView {

    fun onBookDetailsFetched(book: Book)
    fun showError(message: String)
}