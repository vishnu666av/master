package com.example.otchallenge.domain



sealed class RepoResponse<T> {

    data class Success<T>(val data: T) : RepoResponse<T>()

    data class Error<T>(val error: String, val data: T) : RepoResponse<T>()

}