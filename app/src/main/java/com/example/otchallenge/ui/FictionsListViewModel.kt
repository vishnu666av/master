package com.example.otchallenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.otchallenge.model.Fiction
import com.example.otchallenge.model.FictionsDataState
import com.example.otchallenge.model.FictionsListPresenter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject


class FictionsListViewModel @Inject constructor(presenter: FictionsListPresenter) : ViewModel() {

    private val _uiState = MutableStateFlow<FictionsListUiState>(FictionsListUiState.Idle)
    val uiState: StateFlow<FictionsListUiState> get() = _uiState

    init {
        viewModelScope.launch {
            _uiState.update {
                FictionsListUiState.Loading

                when (val result = presenter.getFictions()) {
                    is FictionsDataState.Empty ->
                        FictionsListUiState.Empty(formatDate(result.timestamp))

                    is FictionsDataState.Error -> FictionsListUiState.Error

                    is FictionsDataState.FreshListData -> FictionsListUiState.OnlineData(
                        result.items.map { Fiction.fromBook(it) },
                        formatDate(result.timestamp)
                    )

                    is FictionsDataState.StaleListData -> FictionsListUiState.OfflineData(
                        result.items.map { Fiction.fromBook(it) },
                        formatDate(result.timestamp)
                    )
                }
            }
        }
    }

    // todo: timestamps come as Date() from presenter, and need formatting here.
    private fun formatDate(date: Date): String {
        return date.toString()
    }
}