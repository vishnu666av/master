package com.example.otchallenge.di

import com.example.otchallenge.data.LocalFictionsRepository
import com.example.otchallenge.data.RemoteFictionsRepository
import com.example.otchallenge.data.local.BookDao
import com.example.otchallenge.data.network.NYTimesApiService
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideLocalRepository(bookDao: BookDao) = LocalFictionsRepository(bookDao)

    @Provides
    fun provideRemoteRepository(apiService: NYTimesApiService) =
        RemoteFictionsRepository(apiService)
}