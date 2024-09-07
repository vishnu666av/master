package com.example.otchallenge.data.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NYTimesApiService {

    companion object {
        /**
         * this isn't the ideal place or approach to feed the api key,
         * I'll refactor this if I find the time.
         */
        private const val API_KEY = "KoRB4K5LRHygfjCL2AH6iQ7NeUqDAGAB"
    }

    @GET("svc/books/v3/lists/current/hardcover-fiction.json")
    fun getAllBooks(
        @Query("api-key") apiKey: String = API_KEY,
        @Query("offset") offset: Int = 0
    ): Call<GetBooksResult>
}