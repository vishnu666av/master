package com.example.otchallenge.di

import com.example.otchallenge.MyApplication
import com.example.otchallenge.api.BookApiService
import com.example.otchallenge.api.BookService
import com.example.otchallenge.repository.BookRepository
import com.example.otchallenge.utils.CacheUtils
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private val baseUrl: String, private val application: MyApplication) {

    @Provides
    @Singleton
    fun provideApplication(): MyApplication{
        return application
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideBookApiService(retrofit: Retrofit): BookApiService {
        return retrofit.create(BookApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideBookRepository(apiService: BookApiService): BookRepository{
        return BookRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideCacheUtils(): CacheUtils {
        return CacheUtils
    }

    @Singleton
    @Provides
    fun provideBookService(repository: BookRepository, cacheUtils: CacheUtils): BookService {
        return BookService(repository, cacheUtils)
    }

}