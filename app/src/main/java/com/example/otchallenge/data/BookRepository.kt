package com.example.otchallenge.data

import com.example.otchallenge.BuildConfig
import com.example.otchallenge.api.NycApiService
import com.example.otchallenge.util.logD
import com.example.otchallenge.util.logE
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository @Inject constructor(private val nycApiService: NycApiService) {

    init {
        logD("BookRepository - Singleton Init")
    }

    private val offsetToBookMap = mutableMapOf<Int, List<Book>>()
    suspend fun getBooks(offset: Int): Result<List<Book>> {
        val cachedResult = getFromLocalCache(offset)
        if (cachedResult != null) {
            logD("BookRepository - Returning CachedResult for offset: $offset")
            return cachedResult
        } else {
            return try {
                nycApiService.getHardcoverFictionBooks(
                    BuildConfig.NYTIMES_API_KEY,
                    offset
                ).results.books.map {
                    Book(it.title, it.author, it.description, it.bookImage)
                }.let {
                    offsetToBookMap[offset] = it
                    Result.success(it)
                }
            } catch (e: Exception) {
                logE(e.stackTraceToString(), e)
                Result.failure(e)
            }
        }
    }


    private fun getFromLocalCache(offset: Int): Result<List<Book>>? =
        offsetToBookMap[offset]?.let {
            Result.success(it)
        }
}