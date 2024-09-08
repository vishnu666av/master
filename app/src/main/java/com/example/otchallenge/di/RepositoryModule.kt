package com.example.otchallenge.di

import com.example.otchallenge.data.LocalBooksRepository
import com.example.otchallenge.data.RemoteBooksRepository
import com.example.otchallenge.data.local.BookDao
import com.example.otchallenge.data.network.NYTimesApiService
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideLocalRepository(bookDao: BookDao) = LocalBooksRepository(bookDao)

    @Provides
    fun provideRemoteRepository(apiService: NYTimesApiService) =
        RemoteBooksRepository(apiService)
}