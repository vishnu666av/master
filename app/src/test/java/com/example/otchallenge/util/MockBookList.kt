package com.example.otchallenge.util

import com.example.otchallenge.data.remote.dto.BookDto
import com.example.otchallenge.domain.model.Book


fun mockBookDomainList(): List<Book> {
    return listOf( Book("Jodi Picoult","Ballantine",1, "ALL THE COLORS OF THE DARK",
        "Questions arise when a boy saves the daughter of a wealthy family amid a string of disappearances in a Missouri town in 1975.",
        "https://storage.googleapis.com/du-prd/books/images/9780593798874.jpg"
    ),
        Book("Kristin Hannah","St. Martin's",2,"TOM CLANCY: SHADOW STATE",
            "The 12th book in the Jack Ryan Jr. series. Jack uncovers dangers in Vietnam.",
            "https://storage.googleapis.com/du-prd/books/images/9780593717950.jpg"
        ),
        Book("William Kent Krueger","Atriag",3,"THE COVEN",
            "At Hollow\u2019s Grove University, a school for magic that suffered a bloody massacre decades ago, 13 gifted students confront ghosts from the school\u2019s past.",
            "https://storage.googleapis.com/du-prd/books/images/9781250346742.jpg"
        ),
        )
}

fun mockBookDtoList(): List<BookDto> {
    return listOf(
    )
}
