package com.example.otchallenge.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.otchallenge.data.local.entity.BookEntity

@Dao
interface BookDao {
    @Query("SELECT * FROM book")
    fun getBooks(): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertBooks(anime: List<BookEntity>)

}