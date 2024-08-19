package com.example.otchallenge.network.service

import com.example.otchallenge.network.model.BooksResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Service to fetch book list from the server.
 */
interface BookService {
    @GET("svc/books/v3/lists/current/hardcover-fiction.json")
    suspend fun getBooks(
        @Query("api-key") apikey: String,
        @Query("offset") offset: Int,
    ): Response<BooksResponse>
}