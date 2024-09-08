package com.example.otchallenge.model

import com.example.otchallenge.data.BookDto
import java.util.Date

sealed class FictionsDataState {

    data class Empty(val timestamp: Date) : FictionsDataState()

    data class FreshListData(val items: List<BookDto>, val timestamp: Date) : FictionsDataState()

    data class StaleListData(val items: List<BookDto>, val timestamp: Date) : FictionsDataState()

    data class Error(val message: String?, val timestamp: Date) : FictionsDataState()
}