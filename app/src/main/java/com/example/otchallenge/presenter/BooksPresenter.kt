package com.example.otchallenge.presenter

import com.example.otchallenge.model.BooksData

class BooksPresenter(private val presenterInterface: BooksPresenterInterface) {

    fun presentUi(data: BooksData) {
        if (data.showError) {
            presenterInterface.displayError(data.errorMessage)
        } else {
            presenterInterface.displayLoader(data.isLoading)
            if (data.books.isEmpty()) {
                // ideally we should display an empty data message
                presenterInterface.displayBooks(emptyList())
            } else {
                presenterInterface.displayBooks(data.books)
            }
        }
    }
}
