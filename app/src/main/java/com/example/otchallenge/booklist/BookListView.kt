package com.example.otchallenge.booklist

import com.example.otchallenge.booklist.uistate.BooksContentUIState
import kotlinx.coroutines.CoroutineScope

interface BookListView {
    /**
    * Delivers the state to the UI. */
    fun deliverState(state: BooksContentUIState)

    /**
    * Provides the activity's lifecycle to the presenter. Presenter can run long running tasks in this scope*/
    val ownerLifecycleScope: CoroutineScope
}
