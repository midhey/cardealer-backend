package ru.ladamarket.modelRequest.news

import kotlinx.serialization.Serializable

@Serializable
data class NewsRequest(
    val title: String,
    val content: String
)
