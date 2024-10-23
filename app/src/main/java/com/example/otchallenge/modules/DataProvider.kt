package com.example.otchallenge.modules

import androidx.lifecycle.MutableLiveData
import com.example.otchallenge.api.BooksApi
import com.example.otchallenge.model.BooksData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Data Provider class
 *
 * the data provider of the application, his duty is to retrieve and provide
 * the data to be displayed.
 *
 * Note: open class  for testing purposes
 */
open class DataProvider(private val api: BooksApi) {

    open val booksData: MutableLiveData<BooksData> by lazy {
        MutableLiveData(BooksData())
    }

    open fun getBooks() {
        // disposable should be disposed when view is destroyed
        val disposable = api.getBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                booksData.postValue(
                    booksData.value?.copy(
                        isLoading = true,
                        showError = false,
                        errorMessage = null,
                    )
                )
            }
            .subscribe({
                booksData.postValue(
                    booksData.value?.copy(
                        isLoading = false,
                        books = it.results?.books ?: emptyList(),
                        showError = false,
                        errorMessage = null,
                    )
                )
            }, {
                booksData.postValue(
                    booksData.value?.copy(
                        isLoading = false,
                        showError = true,
                        errorMessage = it.message,
                    )
                )
            })
    }
}
