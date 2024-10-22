package com.example.otchallenge.di

import android.content.Context
import com.example.otchallenge.booklist.BookListComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, UseCaseComponent::class, AppSubComponent::class, SerializationComponent::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun bookListComponentBuilder(): BookListComponent.Builder
}