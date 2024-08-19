package com.example.otchallenge.ui.book.mapper

import com.example.otchallenge.domain.BookListContent
import com.example.otchallenge.domain.BookListViewState
import com.example.otchallenge.domain.BookUIModel
import com.example.otchallenge.network.model.BooksResponse
import com.example.otchallenge.network.service.ApiResult

object BookListMapper {
    /**
     * Converts API response to UI state for book list.
     *
     * @param apiResponse The result from the API call.
     * @return The corresponding UI state for the book list.
     */
    fun convertApiResponseToUiState(
        apiResponse: ApiResult<BooksResponse>
    ): BookListViewState {
        return when (apiResponse) {
            is ApiResult.Success -> {
                // Convert successful response to UI state
                if (apiResponse.data?.results?.books?.isEmpty() == true) {
                    BookListViewState(isEmpty = true)
                } else {
                    BookListViewState(items = mapBooksResponseToUiModel(apiResponse.data))
                }
            }

            is ApiResult.Error -> {
                // Convert failed response to UI state with error message
                BookListViewState(userMessage = apiResponse.message)
            }
        }
    }

    /**
     * Maps books data from the api response to UI models.
     */
    private fun mapBooksResponseToUiModel(
        response: BooksResponse?
    ): List<BookListContent.BookContent> {
        return response?.results?.books?.map { book ->
            BookListContent.BookContent(
                BookUIModel(
                    title = book?.title,
                    description = book?.description,
                    author = book?.author,
                    bookImage = book?.bookImage,
                    rank = book?.rank.toString(),
                    contributor = book?.contributor,
                    publisher = book?.publisher,
                    weeksOnList = book?.weeksOnList ?: 0,
                )
            )
        } ?: emptyList()
    }
}