package com.example.otchallenge.di

import com.example.otchallenge.data.local.BookDao
import com.example.otchallenge.data.local.InMemoryDao
import com.example.otchallenge.data.remote.BooksApi
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
interface DataModule {

    @Binds
    fun providesBookDao(inMemoryDao: InMemoryDao): BookDao

    companion object {
        @Provides
        fun provideStockAPI(): BooksApi =
            Retrofit.Builder().baseUrl("https://api.nytimes.com")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(BooksApi::class.java)

        @Provides
        fun providesInMemoryDao() = InMemoryDao()
    }


}