package com.example.otchallenge.model

data class Fiction(
    val rank: Int,
    val title: String,
    val author: String,
    val description: String,
    val imageUrl: String,
    val buyLinks: List<BuyLink>
) {
    companion object
}

