package com.example.otchallenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.otchallenge.data.BookDto

@Dao
interface BookDao {
    @Insert
    suspend fun insert(bookDto: BookDto)

    @Query("SELECT * FROM books ORDER BY rank")
    suspend fun getAllBooks(): List<BookDto>
}