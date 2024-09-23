package com.example.otchallenge.di

import com.example.otchallenge.MainActivity
import com.example.otchallenge.ui.booklist.BookListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: BookListFragment)
}
