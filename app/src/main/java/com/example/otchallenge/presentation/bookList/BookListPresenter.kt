package com.example.otchallenge.presentation.bookList

import com.example.otchallenge.domain.BaseResult
import com.example.otchallenge.domain.model.Book
import com.example.otchallenge.domain.useCase.getBooks.GetBooksUseCase
import com.example.otchallenge.presentation.base.BasePresenter
import com.example.otchallenge.presentation.bookList.model.BookItemModel
import com.example.otchallenge.presentation.bookList.model.toItemModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BookListPresenter(private val bookUseCase: GetBooksUseCase) :
    BasePresenter<BookListContract.View>(), BookListContract.Presenter {

    private val presenterScope = CoroutineScope(Dispatchers.Main + Job())

    private fun convertBookFlow(books: List<Book>): List<BookItemModel> {
        return books.map { it.toItemModel() }
    }

    private companion object {
        const val TAG = "BookListPresenter"
    }

    override fun loadBooks() {
        getView()?.showLoading()
        presenterScope.launch {

            when (val result = withContext(Dispatchers.IO) {
                bookUseCase.invoke()// This is a suspend function
            }) {
                is BaseResult.Empty -> {
                    getView()?.hideLoading()
                    getView()?.showBooks(emptyList())
                }
                is BaseResult.Error -> {
                    getView()?.hideLoading()
                    getView()?.showError(result.cause.message ?: "Unknown error")
                }
                is BaseResult.Success -> {
                    getView()?.hideLoading()
                    getView()?.showBooks(convertBookFlow(result.value))
                }
            }
        }
    }

    override fun detachView() {
        super.detachView()

        // Cancel the CoroutineScope to stop any ongoing coroutines
        presenterScope.coroutineContext[Job]?.cancel()
    }

}