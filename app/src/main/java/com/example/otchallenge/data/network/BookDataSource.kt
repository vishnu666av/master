package com.example.otchallenge.data.network

import android.util.Log
import com.example.otchallenge.data.models.Book
import com.example.otchallenge.data.models.EmptyArray
import com.example.otchallenge.data.models.ResultWithData
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

const val BASE_URL =
    "https://api.nytimes.com/svc/books/v3/lists/current/"
const val NYTIME_API_KEY = "KoRB4K5LRHygfjCL2AH6iQ7NeUqDAGAB"

class BookDataSource {
    private val bookApi =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                Json { ignoreUnknownKeys = true }.asConverterFactory(
                    "application/json; charset=UTF8".toMediaType(),
                ),
            ).client(
                OkHttpClient
                    .Builder()
                    .addInterceptor(
                        Interceptor { chain ->
                            val originalRequest = chain.request()
                            val requestWithApiKey =
                                originalRequest.newBuilder().url(
                                    originalRequest.url
                                        .newBuilder()
                                        .addQueryParameter(
                                            "api-key",
                                            NYTIME_API_KEY,
                                        ).build(),
                                )
                            val response =
                                chain.proceed(
                                    requestWithApiKey.build(),
                                )

                            try {
                                response.peekBody(Long.MAX_VALUE).string()
                            } catch (e: Throwable) {
                                Log.e("Http Error", "An error occurred here ${e.message}")
                            }
                            response
                        },
                    ).addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        },
                    ).build(),
            ).build()
            .create(BookApi::class.java)

    suspend fun fetchBooks(page: Int): List<Book> {
        val offset = page * 20 // offset (from the api documentation) is in multiples of 20
        return when (val result = bookApi.getBooks(offset).results) {
            EmptyArray -> emptyList()
            is ResultWithData -> result.books
        }
    }
}
