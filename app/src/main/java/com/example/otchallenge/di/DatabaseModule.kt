package com.example.otchallenge.di

import android.content.Context
import androidx.room.Room
import com.example.otchallenge.dao.BookDao
import com.example.otchallenge.repository.BookDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideDatabase(): BookDatabase {
        return Room.databaseBuilder(context, BookDatabase::class.java, "books_db")
            .build()
    }

    @Provides
    fun provideBookDao(bookDatabase: BookDatabase): BookDao{
        return bookDatabase.bookDao()
    }
}