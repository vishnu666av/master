package com.example.otchallenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.otchallenge.data.Book

@Dao
interface BookDao {
    @Insert
    fun insert(book: Book)

    @Query("SELECT * FROM books ORDER BY rank")
    fun getAllBooks(): List<Book>
}