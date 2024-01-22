package ru.ladamarket.database.services.news

import ru.ladamarket.modelRequest.news.NewsRequest
import ru.ladamarket.models.news.News

interface NewsService {
    suspend fun read(id: Int): News?
    suspend fun readAll(): List<News>
    suspend fun create(news: NewsRequest, id: Int)
    suspend fun delete(id: Int)
}