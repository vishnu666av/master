package com.example.otchallenge.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BookResultDto(
    val copyright: String,
    @SerializedName("last_modified")
    val lastModified: String,
    @SerializedName("num_results")
    val numResults: Int,
    val results: Results,
    val status: String
)

data class Results(
    @SerializedName("bestsellers_date")
    val bestsellersDate: String,
    val books: List<BookDto>,
    val corrections: List<Any>,
    @SerializedName("display_name")
    val displayName: String,
    @SerializedName("list_name")
    val listName: String,
    @SerializedName("list_name_encoded")
    val listNameEncoded: String,
    @SerializedName("next_published_date")
    val nextPublishedDate: String,
    @SerializedName("normal_list_ends_at")
    val normalListEndsAt: Int,
    @SerializedName("previous_published_date")
    val previousPublishedDate: String,
    @SerializedName("published_date")
    val publishedDate: String,
    @SerializedName("published_date_description")
    val publishedDateDescription: String,
    val updated: String
)