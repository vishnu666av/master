package com.example.otchallenge.domain.testdata

import com.example.otchallenge.data.network.models.NetBook
import com.example.otchallenge.data.network.models.NetList
import com.example.otchallenge.data.network.models.NetResults

object NetworkTestData {

    fun buildNetList(
        status: String = "OK",
        numResults: Int = 1,
        lastModified: String = BooksConstants.LAST_MODIFIED_DATE,
        results: NetResults = buildNetResults()
    ): NetList = NetList(
        status = status,
        numResults = numResults,
        lastModified = lastModified,
        results = results
    )

    fun buildNetResults(
        listName: String = BooksConstants.LIST_NAME,
        listNameEncoded: String = BooksConstants.LIST_NAME_ENCODED,
        publishedDate: String = BooksConstants.LIST_DATE,
        books: List<NetBook> = listOf(buildNetBook())
    ): NetResults = NetResults(
        listName = listName,
        listNameEncoded = listNameEncoded,
        publishedDate = publishedDate,
        books = books
    )

    fun buildNetBook(
        rank: Int = BooksConstants.BOOK_RANK,
        rankLastWeek: Int = BooksConstants.BOOK_LAST_RANK,
        weeksOnList: Int = BooksConstants.BOOK_WEEKS_ON_LIST,
        primaryIsbn10: String = BooksConstants.BOOK_ISBN,
        title: String = BooksConstants.BOOK_TITLE,
        description: String = BooksConstants.BOOK_DESCRIPTION,
        image: String = BooksConstants.BOOK_IMAGE,
    ): NetBook = NetBook(
        rank = rank,
        rankLastWeek = rankLastWeek,
        weeksOnList = weeksOnList,
        primaryIsbn10 = primaryIsbn10,
        title = title,
        description = description,
        image = image
    )
}
