package com.example.otchallenge.data

import com.example.otchallenge.data.local.BookDao
import com.example.otchallenge.data.remote.BooksApi
import com.example.otchallenge.data.remote.ToBookEntity
import com.example.otchallenge.domain.BookDomain
import com.example.otchallenge.domain.BooksRepo
import com.example.otchallenge.domain.RepoResponse
import com.example.otchallenge.domain.toDomain

class BooksRepoImpl(
    private val booksApi: BooksApi,
    private val bookDao: BookDao,
) : BooksRepo{

    override suspend fun getAllBooks(): RepoResponse<List<BookDomain>> {
        return try {
            val response = booksApi.getAllBooks()
            if (response.isSuccessful) {
                val results = response.body()!!.results.books.map { it.ToBookEntity() }
                bookDao.saveAll(results)
                val storedResults = getResultsFromDao()
                RepoResponse.Success(storedResults)
            } else {
                val storedResults = getResultsFromDao()
                RepoResponse.Error(response.message(), storedResults)
            }
        } catch (ex: Exception) {
            //todo In a prod app I would map the error to a message, but keeping this simple
            val storedResults = getResultsFromDao()
            RepoResponse.Error(ex.message.orEmpty(), storedResults)
        }
    }

    private suspend fun getResultsFromDao() = bookDao.getAll().map { it.toDomain() }


}