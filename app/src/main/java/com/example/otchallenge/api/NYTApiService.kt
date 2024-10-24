package com.example.otchallenge.api

import com.example.otchallenge.api.dto.BookResponseDto
import com.example.otchallenge.define.Constant
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTApiService {

    @GET("lists/current/hardcover-fiction.json")
    suspend fun fetchBooks(
        @Query("api-key") apiKey: String = Constant.API_KEY,
        @Query("offset") offset: Int = 0
    ): BookResponseDto
}