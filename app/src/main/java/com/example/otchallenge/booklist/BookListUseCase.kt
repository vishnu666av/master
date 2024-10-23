package com.example.otchallenge.booklist

import com.example.otchallenge.api.NYTApiService
import com.example.otchallenge.api.dto.BookDto
import com.example.otchallenge.api.dto.BookResponseDto
import com.example.otchallenge.booklist.uistate.BookUIState
import com.example.otchallenge.booklist.uistate.BooksContentUIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/*
* Use case, responsible to fetch the book list from the API endpoint*/
class BookListUseCase @Inject constructor(
    private val apiService: NYTApiService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    suspend operator fun invoke(): BooksContentUIState {
        /**
        * Runs the API call in a background thread */
        return withContext(dispatcher) {
            map(apiService.fetchBooks())
        }
    }

    /**
    * Reduces the Dto to the UI state
    * */
    private fun map(response: BookResponseDto): BooksContentUIState {
        return BooksContentUIState(
            books = response.result.books.map { map(it) }
        )
    }

    private fun map(bookDto: BookDto): BookUIState {
        return BookUIState(
            //** Making the title capitalized (Word by word)*/
            title = bookDto.title.lowercase().split(" ")
                .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } },
            author = bookDto.contributor,
            description = bookDto.description,
            imageUrl = bookDto.bookImage,
            rank = bookDto.rank
        )
    }
}