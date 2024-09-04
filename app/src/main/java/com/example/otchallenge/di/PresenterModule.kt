package com.example.otchallenge.di

import com.example.otchallenge.domain.useCase.getBooks.GetBooksUseCase
import com.example.otchallenge.presentation.bookList.BookListContract
import com.example.otchallenge.presentation.bookList.BookListPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun provideBookListPresenter(
        useCase: GetBooksUseCase
    ): BookListContract.Presenter  {
        return BookListPresenter(useCase)
    }
}