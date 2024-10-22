package com.example.otchallenge.di

import com.example.otchallenge.api.NYTApiService
import com.example.otchallenge.booklist.BookListUseCase
import dagger.Module
import dagger.Provides

@Module
class UseCaseComponent {

    @Provides
    fun provideBookListUseCase(apiService: NYTApiService): BookListUseCase {
        return BookListUseCase(apiService)
    }
}