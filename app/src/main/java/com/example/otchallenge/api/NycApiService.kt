package com.example.otchallenge.api

import retrofit2.http.GET
import retrofit2.http.Query

interface NycApiService {
    @GET("svc/books/v3/lists/current/hardcover-fiction.json")
    suspend fun getHardcoverFictionBooks(
        @Query("api-key") apiKey: String, @Query("offset") offset: Int = 0
    ): BookResponse
}