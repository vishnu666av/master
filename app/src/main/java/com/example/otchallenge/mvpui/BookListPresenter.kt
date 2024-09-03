package com.example.otchallenge.mvpui

import com.example.otchallenge.domain.repository.BooksRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookListPresenter @Inject constructor(
    private val booksRepository: BooksRepository
) : BookListContract.Presenter {
    private var view: BookListContract.View? = null
    private var scope: CoroutineScope? = null

    override fun init(view: BookListContract.View, scope: CoroutineScope) {
        this.view = view
        this.scope = scope
    }

    override fun loadBooks() {
        requireNotNull(view) { "View hasn't being initialized" }
        scope?.let {
            it.launch {
                loadBooksAndPresentInfo()
            }
        }
    }

    private suspend fun loadBooksAndPresentInfo() {
        view?.setLoading()
        booksRepository.fetchDefaultList()
            .onSuccess {
                view?.setBooks(it.books)
            }
            .onFailure {
                view?.setError(it)
            }
    }
}
