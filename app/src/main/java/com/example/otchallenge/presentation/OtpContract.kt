package com.example.otchallenge.presentation

import com.example.otchallenge.domain.BookDomain

interface OtpContract {

    interface View {
        fun setModel(model: Model)
    }

    interface Presenter {
        fun onStart()
        fun onStop()
    }

    sealed class Model(
        val showSpinner: Boolean,
        val showResults: Boolean,
        val showError: Boolean,
    ) {
        data class Success(
            val results: List<Item>
        ) : Model(showSpinner = false, showResults = true, showError = false)

        data object Loading : Model(showSpinner = true, showResults = false, showError = false)
        data object Error : Model(showSpinner = false, showResults = true, showError = true)
    }
}

data class Item(
    val title: String,
    val description: String,
    val imageUrl: String,
)

fun BookDomain.toItem() = Item(
    this.title,
    this.description,
    this.url
)