package ru.ladamarket.modelRequest.news

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val newsId: Int,
    val date: String,
    val authorId: Int,
    val authorName: String,
    val title: String,
    val content: String
)
