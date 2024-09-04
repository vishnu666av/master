package com.example.otchallenge.domain.useCase.getBooks

import com.example.otchallenge.domain.BaseResult
import com.example.otchallenge.domain.mapper.ExceptionMapper
import com.example.otchallenge.domain.model.Book
import com.example.otchallenge.domain.repository.BookRepository
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repository: BookRepository,
    private val exceptionMapper: ExceptionMapper
) {
    suspend operator fun invoke(): BaseResult<List<Book>> =
        runCatching {
            repository.getAllBooks()
        }.fold(
            onSuccess = {
                if (it.isEmpty()) {
                    BaseResult.Empty
                } else {
                    BaseResult.Success(it)
                }
            },
            onFailure = {
                BaseResult.Error(Exception(exceptionMapper.map(it)))
            },
        )
}