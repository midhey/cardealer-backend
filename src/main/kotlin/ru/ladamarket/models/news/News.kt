package ru.ladamarket.models.news

import java.time.LocalDate

data class News(
    val id: Int,
    val date: LocalDate,
    val authorId: Int,
    val title: String,
    val content: String
)
