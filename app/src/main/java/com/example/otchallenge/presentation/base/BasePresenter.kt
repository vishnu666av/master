package com.example.otchallenge.presentation.base

import java.lang.ref.WeakReference

open class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {

    private var viewRef: WeakReference<V>? = null

    override fun attachView(view: V) {
        viewRef = WeakReference(view)
    }

    override fun detachView() {
        viewRef?.clear()
        viewRef = null
    }

    override fun getView(): V? {
        return viewRef?.get()
    }

    override fun isViewAttached(): Boolean {
        return viewRef?.get() != null
    }

    protected fun checkViewAttached() {
        if (!isViewAttached()) throw ViewNotAttachedException()
    }

    class ViewNotAttachedException : RuntimeException("Please call Presenter.attachView(View) before requesting data to the Presenter")
}