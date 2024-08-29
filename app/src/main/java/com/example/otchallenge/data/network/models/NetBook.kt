package com.example.otchallenge.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetBook(
    val rank: Int,
    @SerialName("rank_last_week") val rankLastWeek: Int,
    @SerialName("weeks_on_list") val weeksOnList: Int,
    @SerialName("primary_isbn10") val primaryIsbn10: String,
    val title: String,
    val description: String,
    @SerialName("book_image") val image: String?
)
