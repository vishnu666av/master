package com.example.otchallenge.repository

import com.example.otchallenge.dao.BookDao
import com.example.otchallenge.libs.ApiService
import com.example.otchallenge.model.Book
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class BookRepository (private val apiService: ApiService, private val bookDao: BookDao){

    private val apiKey = "KoRB4K5LRHygfjCL2AH6iQ7NeUqDAGAB&offset=0"

    fun fetchBooks(): Single<List<Book>> {
        return apiService.getBooks(apiKey)
            .map{   // map Apiresponse to a book classes
                response -> response.results.books.map {
                    Book(
                        primary_isbn13 = it.primary_isbn13,
                        rank = it.rank,
                        title = it.title,
                        author = it.author,
                        description = it.description,
                        book_Image = it.book_Image
                    )
                }
            }
            .flatMap{   // Cache the book list in Room DB
                books->bookDao.deleteAllBooks()
                    .andThen(bookDao.insertBooks(books))    // insert books list
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
                            Single.error(it)
                        }
                    }
            }
            .subscribeOn(Schedulers.io()) // Use io scheduler for API operation
    }
}