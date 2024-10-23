package com.example.otchallenge.booklist

import com.example.otchallenge.booklist.uistate.BooksContentUIState
import com.example.otchallenge.booklist.uistate.LoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookListPresenter @Inject constructor(val view: BookListView, val useCase: BookListUseCase) {

    /**
     * State of the UI.
     */
    private var uiState: BooksContentUIState = BooksContentUIState.EMPTY
        set(value) {
            field = value
            view.deliverState(field)
        }

    /**
     * Loads the list of books from the use case.
     * */
    fun loadBooks() {
        uiState = uiState.copy(loadingState = LoadingState.Loading)

        view.ownerLifecycleScope.launch {
            val state = try {
                // ** Load the books **
                val freshContent = useCase()

                // ** Update the UI state, by adding the new books **
                freshContent.copy(books = uiState.books + freshContent.books, loadingState = LoadingState.Ready)
            } catch (exp: Exception) {
                uiState.copy(loadingState = LoadingState.Error(exp))
            }

            // ** Update the UI **
            withContext(Dispatchers.Main) {
                uiState = state
            }
        }
    }

    /**
     * Calls [loadBooks] if the data is not loaded.
     */
    fun reloadIfNeeded() {
        if (uiState.books.isNotEmpty() && uiState.loadingState !is  LoadingState.Loading) {
            return
        }

        loadBooks()
    }
}