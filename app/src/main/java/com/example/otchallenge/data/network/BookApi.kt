package com.example.otchallenge.data.network

import com.example.otchallenge.data.models.Book
import com.example.otchallenge.data.models.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {
    @GET("hardcover-fiction.json")
    suspend fun getBooks(
        @Query("offset") offset: Int,
    ): NetworkResponse<Book>
}
