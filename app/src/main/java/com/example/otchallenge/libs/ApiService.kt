package com.example.otchallenge.libs

import com.example.otchallenge.model.ApiResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("svc/books/v3/lists/current/hardcover-fiction.json")
    fun getBooks(
        @Query("api-key") apiKey: String
    ): Single<ApiResponse>
}
