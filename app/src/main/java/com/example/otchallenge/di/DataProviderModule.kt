package com.example.otchallenge.di

import com.example.otchallenge.api.BooksApi
import com.example.otchallenge.modules.DataProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataProviderModule {

    @Provides
    @Singleton
    fun provideDataProvider(api: BooksApi): DataProvider {
        return DataProvider(api)
    }
}
