package com.example.otchallenge.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.otchallenge.dao.BookDao
import com.example.otchallenge.model.Book

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao() : BookDao
}