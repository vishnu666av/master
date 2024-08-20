package com.example.otchallenge.di

import com.example.otchallenge.domain.BooksRepo
import com.example.otchallenge.presentation.OtpContract
import com.example.otchallenge.presentation.OtpPresenter
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [PresentationModule.Bindings::class])
class PresentationModule(private val activity: OtpContract.View) {

    @Provides
    fun providesPresenter(booksRepo: BooksRepo) = OtpPresenter(activity, booksRepo)

    @Module
    interface Bindings {

        @Binds
        fun bindsOtpPresenter(otpPresenter: OtpPresenter): OtpContract.Presenter

    }

}