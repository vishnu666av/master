package com.example.otchallenge.util

import com.example.otchallenge.R
import com.example.otchallenge.domain.BookListViewState

/**
 * Represents the state of a page, including loading, error, and content display states.
 *
 * @property heroImageResId Resource ID of the hero image to display.
 * @property title Title text to display.
 * @property message Message text to display.
 * @property isLoading Indicates if the page is currently loading.
 */
data class PageState(
    val heroImageResId: Int = 0,
    val title: String? = null,
    val message: String? = null,
    val isLoading: Boolean = false
)

/**
 * Utility object for handling the UI state of pages, converting [BookListViewState] to [PageState].
 */
object PageStateUtil {
    /**
     * Converts [BookListViewState] to [PageState] for UI rendering.
     *
     * @param uiState The current UI state of the books list.
     * @param isNetworkConnected The network connectivity status.
     * @return A [PageState] object representing the UI state for rendering.
     */
    fun getPageState(uiState: BookListViewState, isNetworkConnected: Boolean): PageState? {
        return when {
            uiState.isLoading -> {
                PageState(isLoading = true)
            }

            !uiState.userMessage.isNullOrEmpty() -> {
                PageState(
                    heroImageResId = R.drawable.ic_error,
                    title = if (isNetworkConnected) {
                        "Error Parsing Response"
                    } else {
                        "No Network Connection"
                    },
                    message = uiState.userMessage
                )
            }

            uiState.items.isEmpty() -> {
                PageState(
                    heroImageResId = R.drawable.ic_error,
                    title = "No Books Found",
                    message = "It looks like there are no books in the catalog yet."
                )
            }

            else -> null
        }
    }
}