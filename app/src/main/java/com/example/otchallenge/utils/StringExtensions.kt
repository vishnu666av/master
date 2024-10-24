package com.example.otchallenge.utils

/**
 * Capitalizes the first letter of each word in a string.
 */
fun String.capitalizeWords() = this.lowercase().split(" ")
    .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }