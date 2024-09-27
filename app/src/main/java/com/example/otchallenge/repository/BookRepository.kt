package com.example.otchallenge.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.otchallenge.dao.BookDao
import com.example.otchallenge.libs.ApiService
import com.example.otchallenge.model.Book
import com.google.gson.Gson
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class BookRepository (private val apiService: ApiService, private val bookDao: BookDao){

    private val apiKey = "KoRB4K5LRHygfjCL2AH6iQ7NeUqDAGAB"
    private val gson = Gson() // Gson instance for conversion

    fun fetchBooks(): Single<List<Book>> {
        return apiService.getBooks(apiKey)
            .subscribeOn(Schedulers.io())   // Running network task on io scheduler
            .map{   // map Apiresponse to a book classes
                response ->

                // Convert response to plain text (JSON) and log it
                val plainTextResponse = gson.toJson(response)
                Log.d("API_RESPONSE", plainTextResponse) // This will output the entire response as a plain text JSON string

                response.results.books.map {
                    println(it)
                    Book(
                        primary_isbn13 = it.primary_isbn13,
                        rank = it.rank,
                        title = it.title,
                        author = it.author,
                        description = it.description,
                        book_image = it.book_image
                    )
                }
            }
            .flatMap{   // Cache the book list in Room DB
                books->bookDao.deleteAllBooks()
                    .andThen(bookDao.insertBooks(books))    // insert books list
                    .doOnComplete {
                        Log.d("DB_INSERT", "Books successfully inserted into the database")
                    }
                    .andThen(Single.just(books))    // emit the books list
            }
            .onErrorResumeNext {
                // If network fails, fetch from the DB
                bookDao.getAllBooks()
                    .subscribeOn(Schedulers.io())   // Use io scheduler for DB operation
                    .flatMap { cachedBooks ->
                        if (cachedBooks.isNotEmpty()) {
                            Single.just(cachedBooks)
                        }else{
                            //Single.error(it)
                            Single.just(emptyList())
                        }
                    }
            }
            .observeOn(AndroidSchedulers.mainThread()) // Process UI task on mainThread
    }
}