package com.example.otchallenge.api

import android.content.Context
import com.example.otchallenge.model.Book
import com.example.otchallenge.repository.BookRepository
import com.example.otchallenge.utils.CacheUtils
import com.example.otchallenge.utils.NetworkUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Single
import javax.inject.Inject

class BookService @Inject constructor(
    private val repository: BookRepository,
    private val cacheUtils: CacheUtils
) {

    fun getBooks(apiKey: String, offset:Int, context: Context) : Single<List<Book>> {
        return if(NetworkUtils.isNetworkAvailable(context)){
            repository.getBooks(apiKey, offset)
                .doOnSuccess{cacheBooks(it.results.books, context)}
                .map { it.results.books }
                .onErrorResumeNext { getCachedBooks(context) }
        }else{
            getCachedBooks(context)
        }
    }

    private fun cacheBooks(books: List<Book>, context: Context) {
        cacheUtils.saveBooks(context, Gson().toJson(books))
    }

    private fun getCachedBooks(context: Context) : Single<List<Book>> {
        val booksJson = cacheUtils.getBooks(context) ?: return  Single.error(Throwable("No cached data available"))
        val booksType = object : TypeToken<List<Book>>() {}.type
        val books: List<Book> = Gson().fromJson(booksJson, booksType)
        return Single.just(books)
    }

    fun getBookDetails(bookId: Int, context: Context): Book? {
        val booksJson = CacheUtils.getBooks(context) ?: return null
        val booksType = object : TypeToken<List<Book>>() {}.type
        val books : List<Book> = Gson().fromJson(booksJson, booksType)
        return books.find { it.rank == bookId }
    }

}