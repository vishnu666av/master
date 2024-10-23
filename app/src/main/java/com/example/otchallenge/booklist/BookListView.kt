package com.example.otchallenge.booklist

import androidx.lifecycle.LifecycleCoroutineScope
import com.example.otchallenge.booklist.uistate.BooksContentUIState

interface BookListView {
    /**
    * Delivers the state to the UI. */
    fun deliverState(state: BooksContentUIState)

    /**
    * Provides the activity's lifecycle to the presenter. Presenter can run long running tasks in this scope*/
    val ownerLifecycleScope: LifecycleCoroutineScope
}
