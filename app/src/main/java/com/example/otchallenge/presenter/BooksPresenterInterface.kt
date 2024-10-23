package com.example.otchallenge.presenter

import com.example.otchallenge.model.BookModel

/**
 * Books presenter interface
 *
 * interface that defines the contract between the view and the Presenter Class
 */
interface BooksPresenterInterface {

    fun displayBooks(books: List<BookModel>)

    fun displayLoader(showLoader: Boolean)

    fun displayError(message: String?)
}
