package com.example.otchallenge.di

import com.example.otchallenge.ui.main.BookListContract
import com.example.otchallenge.ui.main.BookListPresenter
import com.example.otchallenge.ui.main.BookListView
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
interface MvpModule {
    @Binds
    fun bindsPresenter(impl: BookListPresenter): BookListContract.BookListPresenter

    @Binds
    fun bindsView(impl: BookListView): BookListContract.BookListView
}
