package com.example.otchallenge.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetList(
    val status: String,
    @SerialName("num_results") val numResults: Int,
    @SerialName("last_modified") val lastModified: String,
    val results: NetResults
)

@Serializable
data class NetResults(
    @SerialName("list_name") val listName: String,
    @SerialName("list_name_encoded") val listNameEncoded: String,
    @SerialName("published_date") val publishedDate: String,
    val books: List<NetBook>
)
