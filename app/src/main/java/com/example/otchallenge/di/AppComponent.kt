package com.example.otchallenge.di

import com.example.otchallenge.MainActivity
import com.example.otchallenge.view.BookDetailFragment
import com.example.otchallenge.view.BookListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
	fun inject(activity: MainActivity)
	fun inject(fragment: BookListFragment)
	fun inject(fragment: BookDetailFragment)
}
