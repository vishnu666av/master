package com.example.otchallenge.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.otchallenge.data.BookDto

@Dao
interface BookDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<BookDto>)

    @Transaction
    @Query("SELECT * FROM books ORDER BY rank")
    suspend fun getAll(): List<BookDto>
}