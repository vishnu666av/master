package com.example.otchallenge.mvpui

import com.example.otchallenge.domain.models.Book
import kotlinx.coroutines.CoroutineScope

interface BookListContract {
    interface Presenter {
        fun init(view: View, scope: CoroutineScope)
        fun loadBooks()
    }

    interface View {
        fun setBooks(books: List<Book>)
        fun setLoading()
        fun setError(throwable: Throwable)
    }
}
