package com.example.otchallenge.booklist

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookListPresenter @Inject constructor(val view: BookListView, val useCase: BookListUseCase) {

    /**
    * Loads the list of books from the use case.
    * */
    fun loadBooks() {
        view.updateLoadingState(LoadingState.Loading)

        view.ownerLifecycleScope.launch {
            val status = try {
                LoadingState.Ready(useCase())
            } catch (exp: Exception) {
                LoadingState.Error(exp)
            }

            withContext(Dispatchers.Main) {
                view.updateLoadingState(status)
            }
        }
    }
}