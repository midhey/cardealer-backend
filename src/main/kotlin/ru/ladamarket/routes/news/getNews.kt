package ru.ladamarket.routes.news

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.news.NewsService
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.modelRequest.news.NewsResponse

fun Route.getNews(
    newsService: NewsService,
    userService: UserService
) {
    get("/read/{newsId}") {
        val newsId = call.parameters["newsId"]!!.toInt()
        val news = newsService.read(newsId)
        if (news != null) {

            val userName = userService.readFullName(news.authorId)
            userName?.let{
                call.respond(
                    HttpStatusCode.OK,
                    NewsResponse(
                        newsId = news.id,
                        date = news.date.toString(),
                        authorId = news.authorId,
                        authorName = userName,
                        title = news.title,
                        content = news.content
                    )
                )

            }
        }
    }
}