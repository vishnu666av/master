package com.example.otchallenge.presenter

import com.example.otchallenge.libs.ApiService
import com.example.otchallenge.model.ApiResponse
import com.example.otchallenge.repository.BookRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class BooksPresenter(private val repository: BookRepository) : BooksContract.Presenter{
    private var view: BooksContract.View? = null
    private val disposable = CompositeDisposable()

    override fun attach(view: BooksContract.View) {
        this.view = view
    }

    override fun detach() {
        view = null
        disposable.clear()
    }

    override fun fetchBooks() {
        view?.showProgress()

        val disposableTask = repository.fetchBooks()
            .observeOn(AndroidSchedulers.mainThread())  // Use MainThread for Book list updates
            .subscribe({books ->
                view?.hideProgress()
                view?.displayBooks(books)
            }, {error ->
                view?.hideProgress()
                view?.showError(error.message ?: "An error occurred")
            })

        disposable.add(disposableTask)
    }
}