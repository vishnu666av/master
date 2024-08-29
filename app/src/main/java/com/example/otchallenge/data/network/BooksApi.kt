package com.example.otchallenge.data.network

import com.example.otchallenge.BuildConfig
import com.example.otchallenge.data.network.models.NetList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi {

    @GET("lists/current/{listName}?api-key=${BuildConfig.API_KEY}")
    suspend fun fetchCurrentList(
        @Path("listName") listName: String = "hardcover-fiction.json",
        @Query("offset") offset: Int = 0
    ): Response<NetList>

}

