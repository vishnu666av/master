package com.example.otchallenge.data.repository

import androidx.core.net.toUri
import com.example.otchallenge.BuildConfig
import com.example.otchallenge.data.local.entity.BookEntity
import com.example.otchallenge.data.remote.BookApi
import com.example.otchallenge.common.util.ConnectivityProvider
import com.example.otchallenge.data.local.room.BookDao
import com.example.otchallenge.domain.model.Book
import com.example.otchallenge.domain.repository.BookRepository
import com.example.otchallenge.data.remote.dto.BookDto
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val api: BookApi,
    private val dao: BookDao,
    private val connectivityProvider: ConnectivityProvider
) : BookRepository {

    override suspend fun getAllBooks(): List<Book> {

        if (connectivityProvider.isNetworkAvailable()) {
            val response = api.getBooks(BuildConfig.API_KEY, 0)
            val databaseClips = response.results.books.map { it.toDB() }
            dao.insertBooks(databaseClips)
            return databaseClips.map { it.toDomain() }
        } else {
            val dbClips = dao.getBooks()
            return dbClips.map {  it.toDomain() }
        }

    }

    private fun BookDto.toDB() = BookEntity(
        bookUri = bookUri.toUri(),
        author = author,
        bookImage = bookImage,
        bookImageHeight = bookImageHeight,
        bookImageWidth = bookImageWidth,
        contributor = contributor,
        contributorNote = contributorNote,
        description = description,
        price = price,
        publisher = publisher,
        rank = rank,
        rankLastWeek = rankLastWeek,
        title = title,
        weeksOnList = weeksOnList,
    )

    private fun BookEntity.toDomain() = Book(
        author = author,
        publisher = publisher,
        rank = rank,
        title = title,
        imageUrl = bookImage,
        description = description
    )

}