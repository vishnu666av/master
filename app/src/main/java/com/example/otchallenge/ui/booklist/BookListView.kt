package com.example.otchallenge.ui.booklist

import com.example.otchallenge.data.Book
import kotlinx.coroutines.CoroutineScope

/**
 * Interface that defines the View for the [BookListPresenter]
 */
interface BookListView {
    fun showLoading()
    fun hideLoading()
    fun showError(message: String)
    fun getLifecycleScope(): CoroutineScope
    fun addBooks(books: List<Book>)
}
