package com.example.otchallenge.presentation.base

interface BaseContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showError(message: String)
    }

    interface Presenter<V : View> {
        fun attachView(view: V)
        fun detachView()
        fun getView(): V?
        fun isViewAttached(): Boolean
    }
}