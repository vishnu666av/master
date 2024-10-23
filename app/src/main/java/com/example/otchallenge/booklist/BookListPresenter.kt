package com.example.otchallenge.booklist

import com.example.otchallenge.booklist.uistate.BooksContentUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookListPresenter @Inject constructor(val view: BookListView, val useCase: BookListUseCase) {

    /**
     * State of the UI.
     */
    private var uiState: BooksContentUIState = BooksContentUIState.EMPTY

    /**
     * Loads the list of books from the use case.
     * */
    fun loadBooks() {
        view.updateLoadingState(LoadingState.Loading)

        view.ownerLifecycleScope.launch {
            val status = try {
                // ** Load the books **
                val freshContent = useCase()

                // ** Update the UI state, by adding the new books **
                uiState = freshContent.copy(books = uiState.books + freshContent.books)

                LoadingState.Ready(uiState)
            } catch (exp: Exception) {
                LoadingState.Error(exp)
            }

            // ** Update the UI **
            withContext(Dispatchers.Main) {
                view.updateLoadingState(status)
            }
        }
    }

    /**
     * Calls [loadBooks] if the data is not loaded.
     */
    fun reloadIfNeeded() {
        if (uiState.books.isNotEmpty()) {
            return
        }

        loadBooks()
    }
}