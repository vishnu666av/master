package com.example.otchallenge.repository

import com.example.otchallenge.BuildConfig
import com.example.otchallenge.network.model.BooksResponse
import com.example.otchallenge.network.service.ApiResult
import com.example.otchallenge.network.service.BookService
import javax.inject.Inject

/**
 * Repository for fetching books data.
 */
class BookRepository @Inject constructor(private val bookService: BookService) {
    suspend fun getBooks(offset: Int = 0): ApiResult<BooksResponse> {
        return ApiResult.create(
            bookService.getBooks(
                apikey = BuildConfig.API_KEY,
                offset = offset // Pass the offset from the ViewModel to handle pagination.
            )
        )
    }
}