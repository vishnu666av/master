package com.example.otchallenge.domain

import com.example.otchallenge.util.Constants

/**
 * UI model for displaying an book in the UI.
 */
data class BookUIModel(
    val title: String? = null,
    val description: String? = null,
    val author: String? = null,
    val bookImage: String? = null,
    val rank: String? = null,
    val publisher: String? = null,
    val contributor: String? = null,
    val weeksOnList: Int,
) {
    fun getAuthorAndPublisherText() = "$contributor | $publisher"

    /**
     * Returns a string representing the number of weeks the book has been on the list.
     */
    fun getWeeksOnListText(): String {
        return if (weeksOnList <= 1) {
            Constants.NEW_THIS_WEEK
        } else {
            "$weeksOnList ${Constants.WEEKS_ON_THE_LIST}"
        }
    }
}