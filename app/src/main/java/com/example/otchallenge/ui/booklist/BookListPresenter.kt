package com.example.otchallenge.ui.booklist

import com.example.otchallenge.data.BookRepository
import com.example.otchallenge.util.logD
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BookListPresenter @Inject constructor(private val bookRepository: BookRepository) {
    companion object {
        private const val PAGE_SIZE = 20
        private const val TAG = "BookListPresenter"
    }

    private var view: BookListView? = null

    /**
     * Save the job so we can cancel it (when load books is called again or in case when
     * view is destroyed)
     */
    private var job: Job? = null
    private var currentOffset = 0
    var isLoading = false
        private set
    var hasMoreData = true
        private set

    init {
        logD("$TAG - Init")
    }

    fun attachView(view: BookListView) {
        this.view = view
    }

    fun detachView() {
        this.view = null
        job?.cancel()
    }

    fun loadInitialData() {
        if (isLoading) return
        isLoading = true
        currentOffset = 0
        hasMoreData = true
        view?.showLoading()
        loadBooks()
    }

    fun loadMoreData() {
        // Avoid aggressive loading if somehow loadMoreData is called unexpectedly
        if (isLoading || !hasMoreData) return
        isLoading = true
        loadBooks()
    }

    private fun loadBooks() {
        job?.cancel()
        job = view?.getLifecycleScope()?.launch {
            view?.showLoading()
            val result = bookRepository.getBooks(0)
            if (result.isSuccess) {
                val books = result.getOrDefault(listOf())
                view?.addBooks(books)
                currentOffset += PAGE_SIZE
                hasMoreData = books.size >= PAGE_SIZE
                logD("$TAG - hasMoreData: $hasMoreData")
            } else {
                if (currentOffset == 0) {
                    view?.showError("Error loading initial books")
                } else {
                    view?.showError("Error loading more books")
                }
            }
            view?.hideLoading()
        }
    }
}