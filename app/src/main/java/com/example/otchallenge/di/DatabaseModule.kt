package com.example.otchallenge.di

import android.content.Context
import com.example.otchallenge.model.local.BookDao
import com.example.otchallenge.model.local.BooksDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val application: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideDatabase(context: Context): BooksDatabase = BooksDatabase.getDatabase(context)

    @Provides
    fun provideBookDao(database: BooksDatabase): BookDao = database.bookDao()
}