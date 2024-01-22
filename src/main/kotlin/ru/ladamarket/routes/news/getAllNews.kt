package ru.ladamarket.routes.news

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.news.NewsService
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.modelRequest.news.NewsResponse

fun Route.getAllNews(
    newsService: NewsService,
    userService: UserService
) {
    get("/read-all") {
        val news = newsService.readAll()
        if (news.isNotEmpty()) {
            val newsResponses = news.map { newsItem ->
                val userName = userService.readFullName(newsItem.authorId)
                userName?.let{
                    NewsResponse(
                        newsId = newsItem.id,
                        date = newsItem.date.toString(),
                        authorId = newsItem.authorId,
                        authorName = userName,
                        title = newsItem.title,
                        content = newsItem.content
                    )
                }
            }
            if (newsResponses.isNotEmpty()) {
                call.respond(
                    HttpStatusCode.OK,
                    newsResponses
                )
            }
        }
    }
}