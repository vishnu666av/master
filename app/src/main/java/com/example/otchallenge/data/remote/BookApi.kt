package com.example.otchallenge.data.remote

import com.example.otchallenge.data.remote.dto.BookResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {
    @GET("svc/books/v3/lists/current/hardcover-fiction.json?")
    suspend fun getBooks(
        @Query("api-key") apikey: String,
        @Query("offset") offset: Int,
    ) : BookResultDto
}