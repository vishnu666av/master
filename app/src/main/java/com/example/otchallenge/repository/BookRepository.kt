package com.example.otchallenge.repository

import com.example.otchallenge.api.BookApiService
import com.example.otchallenge.model.BookResponse
import io.reactivex.Single
import javax.inject.Inject

class BookRepository  @Inject constructor(private val bookApiService: BookApiService){

        fun getBooks(apiKey: String, offset:Int): Single<BookResponse>{
            return bookApiService.getBooks(apiKey, offset)
        }
}