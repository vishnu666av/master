package com.example.otchallenge.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.otchallenge.model.Book

@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY rank ASC")
    fun getAllBooks(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBooks(books: List<Book>)

    @Query("DELETE FROM books")
    fun deleteAllBooks()
}
