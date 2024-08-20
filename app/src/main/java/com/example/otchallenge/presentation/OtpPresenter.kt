package com.example.otchallenge.presentation

import com.example.otchallenge.domain.BooksRepo
import com.example.otchallenge.domain.RepoResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class OtpPresenter(
    private val view: OtpContract.View,
    private val booksRepo: BooksRepo,
    private val parentJob: Job = Job(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.Main,
) : OtpContract.Presenter {

    private val coroutineContext: CoroutineContext
        get() = parentJob + dispatcher

    private val scope = CoroutineScope(coroutineContext)

    override fun onStart() {

        scope.launch {
            view.setModel(OtpContract.Model.Loading)
            when (val response = booksRepo.getAllBooks()) {
                is RepoResponse.Error -> {
                    view.setModel(OtpContract.Model.Error)
                }

                is RepoResponse.Success -> {
                    view.setModel(OtpContract.Model.Success(response.data.map { it.toItem() }))
                }
            }
        }
    }

    override fun onStop() {
        parentJob.cancel()
    }
}