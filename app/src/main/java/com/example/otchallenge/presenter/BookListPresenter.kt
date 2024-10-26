package com.example.otchallenge.presenter

import android.content.Context
import android.util.Log
import com.example.otchallenge.api.BookService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BookListPresenter @Inject constructor(
    private val service: BookService
) {

    private val disposable = CompositeDisposable()
    private var bookListView: BookListView? = null
    private var bookDetailView: BookDetailView? = null

    fun attachListView(view: BookListView){
        this.bookListView = view
    }

    fun attachDetailView(view: BookDetailView){
        this.bookDetailView = view
    }

    fun detachView(){
        disposable.clear()
        bookListView = null
        bookDetailView = null
    }

    fun fetchBooks(apiKey: String, offset: Int, context: Context){
        //Log.d("BookListPresenter", "data fetch called")
        disposable.add(
            service.getBooks(apiKey, offset, context)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        books -> bookListView?.onBooksFetched(books)
                        //Log.d("BookListPresenter", "response success = " + books)
                    },
                    {
                        error -> bookListView?.showError(error.message ?: "An error occurred")
                        //Log.d("BookListPresenter", "response failed = " + error.message)

                    }
                )
        )
    }

    fun getBookDetails(bookId: Int, context: Context) {
        val book = service.getBookDetails(bookId, context)
        if (book != null) {
            bookDetailView?.onBookDetailsFetched(book)
        } else {
            bookDetailView?.showError("Book details not found in cache")
        }
    }
}