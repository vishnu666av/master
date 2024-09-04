package com.example.otchallenge.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.otchallenge.data.local.entity.BookEntity

@Database(entities = [BookEntity::class], version = 1)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}