package com.example.otchallenge.mvpui.di

import com.example.otchallenge.mvpui.BookListContract
import com.example.otchallenge.mvpui.BookListPresenter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BooksListMVPModule {
    @Binds
    abstract fun bindsBookListPresenter(bookListPresenter: BookListPresenter): BookListContract.Presenter
}
