package com.example.otchallenge.di

import com.example.otchallenge.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkProviderModule::class,
        DataProviderModule::class,
        ImageProviderModule::class
    ]
)
interface AppComponent {
    fun inject(activity: MainActivity)
}
