package com.example.otchallenge.domain.fakes

import com.example.otchallenge.domain.BookDomain
import com.example.otchallenge.domain.BooksRepo
import com.example.otchallenge.domain.RepoResponse

class FakeBooksRepo(var response: RepoResponse<List<BookDomain>>) : BooksRepo {

    override suspend fun getAllBooks(): RepoResponse<List<BookDomain>> {
        return response
    }
}