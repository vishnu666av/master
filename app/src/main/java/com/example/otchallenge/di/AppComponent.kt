package com.example.otchallenge.di

import com.example.otchallenge.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [PresentationModule::class, DataModule::class, DomainModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
}
