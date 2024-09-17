package com.example.otchallenge.ui.book.usecase

import com.example.otchallenge.ui.book.mapper.BookListMapper
import com.example.otchallenge.domain.BookListViewState
import com.example.otchallenge.domain.PageStateOption
import com.example.otchallenge.repository.BookRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

/**
 * Handles business logic related to fetching and processing books data.
 *
 * @property bookRepository Provides access to books data.
 */
class BookListUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {

    /**
     * Fetches books from the repository and converts the response to UI state.
     *
     * @return The UI state representing the result of fetching books.
     */
    suspend fun fetchBooks(
        pageStateOption: PageStateOption = PageStateOption.DEFAULT,
        offset: Int
    ): BookListViewState {
        return try {
            val response = when (pageStateOption) {

                PageStateOption.DEFAULT -> {
                    bookRepository.getBooks(offset)
                }

                PageStateOption.LOADING -> {
                    delay(2000)
                    bookRepository.getBooks(offset)
                }

                PageStateOption.ERROR -> {
                    bookRepository.getBooks(-20)
                }

                PageStateOption.EMPTY -> {
                    bookRepository.getBooks(offset)
                }
            }

            val uiState = BookListMapper.convertApiResponseToUiState(response)

            if (pageStateOption == PageStateOption.EMPTY) {
                uiState.copy(items = emptyList())
            } else {
                uiState
            }
        } catch (e: Exception) {
            BookListViewState(userMessage = e.message)
        }
    }
}