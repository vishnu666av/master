package com.example.otchallenge.data.remote

import com.example.otchallenge.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {


    @GET("svc/books/v3/lists/current/hardcover-fiction.json")
    suspend fun getAllBooks(
        @Query("api-key") apikey: String = BuildConfig.apiKey,
        @Query("offset") offset: Int =0,
    ): Response<ResponseDto>

}