package com.example.otchallenge.ui

import com.example.otchallenge.model.Fiction

sealed class FictionsListUiState {

    /**
     * this is the initial state of the ui before it attempts to do any meaningful operations.
     */
    data object Idle : FictionsListUiState()

    data object Loading : FictionsListUiState()

    data class Empty(val timestamp: String) : FictionsListUiState()

    data class OfflineData(val items: List<Fiction>, val timestamp: String) : FictionsListUiState()

    data class OnlineData(val items: List<Fiction>, val timestamp: String) : FictionsListUiState()

    data object Error : FictionsListUiState()
}