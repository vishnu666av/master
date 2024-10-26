package com.example.otchallenge.api

import com.example.otchallenge.model.BookResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApiService {

    @GET("svc/books/v3/lists/current/hardcover-fiction.json")
    fun getBooks(
        @Query("api-key") apiKey: String,
        @Query("offset") offset: Int
    ): Single<BookResponse>
}