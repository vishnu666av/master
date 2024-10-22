package com.example.otchallenge.booklist

import androidx.lifecycle.LifecycleCoroutineScope
import com.example.otchallenge.booklist.uistate.BooksContentUIState

interface BookListView {
    /*
    * Delivers the loading state to the view */
    fun updateLoadingState(loadingState: LoadingState<BooksContentUIState>)

    /*
    * Provides the activity's lifecycle to the presenter. Presenter can run long running tasks in this scope*/
    val ownerLifecycleScope: LifecycleCoroutineScope
}
