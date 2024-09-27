package com.example.otchallenge.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.otchallenge.model.Book
import io.reactivex.Single
import io.reactivex.Completable

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY rank ASC")
    fun getAllBooks(): Single<List<Book>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(books: List<Book>): Completable

    @Query("DELETE FROM books")
    fun deleteAllBooks(): Completable
}
