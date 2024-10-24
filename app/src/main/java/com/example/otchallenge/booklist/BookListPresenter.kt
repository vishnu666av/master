package com.example.otchallenge.booklist

import com.example.otchallenge.api.dto.BookDto
import com.example.otchallenge.api.dto.BookResponseDto
import com.example.otchallenge.booklist.uistate.BookUIState
import com.example.otchallenge.booklist.uistate.BooksContentUIState
import com.example.otchallenge.booklist.uistate.LoadingState
import com.example.otchallenge.utils.capitalizeWords
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookListPresenter @Inject constructor(val view: BookListView, val useCase: BookListUseCase) {

    /**
     * State of the UI.
     */
    var uiState: BooksContentUIState = BooksContentUIState.EMPTY
        private set(value) {
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
                val freshContent = map(useCase())

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
        if (uiState.books.isNotEmpty() || uiState.loadingState is  LoadingState.Loading) {
            return
        }

        loadBooks()
    }


    /**
     * Reduces the Dto to the UI state
     * */
    private fun map(response: BookResponseDto): BooksContentUIState {
        return BooksContentUIState.EMPTY.copy(
            books = response.result.books.map { map(it) },
            listName = response.result.listName,
            lastModified = response.lastModified
        )
    }

    private fun map(bookDto: BookDto): BookUIState {
        return BookUIState(
            //** Making the title capitalized (Word by word)*/
            title = bookDto.title.capitalizeWords(),
            author = bookDto.contributor,
            description = bookDto.description,
            imageUrl = bookDto.bookImage,
            rank = bookDto.rank
        )
    }
}