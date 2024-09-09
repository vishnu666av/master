package com.example.otchallenge.data.network

import com.example.otchallenge.data.models.NetworkResponse
import com.example.otchallenge.data.models.ResponseData
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {
    @GET("hardcover-fiction.json")
    suspend fun getBooks(
        @Query("offset") offset: Int,
    ): NetworkResponse<ResponseData>
}
