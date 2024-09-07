package com.example.otchallenge.data

import com.example.otchallenge.data.network.GetBooksResult
import com.example.otchallenge.data.network.NYTimesApiService
import com.example.otchallenge.model.Fiction
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RemoteFictionsRepository @Inject constructor(private val apiService: NYTimesApiService) :
    Repository<Fiction> {

    override fun all(): List<Fiction> {
        val fictions = mutableListOf<Fiction>()
        apiService.getAllBooks().enqueue(object : Callback<GetBooksResult> {
            override fun onResponse(
                request: Call<GetBooksResult>,
                response: Response<GetBooksResult>
            ) {
                fictions.addAll(
                    response.body()?.results?.books.orEmpty().map { Fiction.fromBook(it) })
            }

            override fun onFailure(request: Call<GetBooksResult>, throwable: Throwable) {
                throw IOException("failed to call api service")
            }
        })

        return fictions
    }
}