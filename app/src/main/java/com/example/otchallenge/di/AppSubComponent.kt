package com.example.otchallenge.di

import com.example.otchallenge.booklist.BookListComponent
import dagger.Module

@Module(subcomponents = [BookListComponent::class])
interface AppSubComponent