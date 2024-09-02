package com.example.otchallenge.di

import com.example.otchallenge.data.network.BookDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun providesBookDataSource() = BookDataSource()
}
