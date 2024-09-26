package com.example.otchallenge.di

import android.content.Context
import com.example.otchallenge.dao.BookDao
import com.example.otchallenge.libs.ApiService
import com.example.otchallenge.repository.BookRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideBookRepository(apiService: ApiService, bookDao: BookDao): BookRepository{
        return BookRepository(apiService, bookDao)
    }
}