package com.example.otchallenge.api

import com.example.otchallenge.BOOKS_API_KEY
import com.example.otchallenge.model.BooksResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BooksApi {
    @GET("svc/books/v3/lists/current/hardcover-fiction.json?&api-key=${BOOKS_API_KEY}")
    fun getBooks(@Query("offset") offset: Int = 0): Single<BooksResponseModel>
}
