package com.example.otchallenge.api

import com.example.otchallenge.define.Constant
import com.example.otchallenge.dto.BookResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTApiService {

    @GET("lists/current/hardcover-fiction.json")
    suspend fun fetchBooks(
        @Query("api-key") apiKey: String = Constant.API_KEY,
        @Query("offset") offset: Int = 0
    ): BookResponseDto
}