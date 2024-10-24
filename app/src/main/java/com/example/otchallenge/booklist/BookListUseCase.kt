package com.example.otchallenge.booklist

import com.example.otchallenge.api.NYTApiService
import com.example.otchallenge.api.dto.BookResponseDto
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

    suspend operator fun invoke(): BookResponseDto {
        /**
         * Runs the API call in a background thread */
        return withContext(dispatcher) {
            apiService.fetchBooks()
        }
    }

}