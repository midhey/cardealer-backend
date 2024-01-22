package ru.ladamarket.routes.news

import io.ktor.server.routing.*
import ru.ladamarket.database.services.news.NewsService
import ru.ladamarket.database.services.user.UserService

fun Route.news(
    newsService: NewsService,
    userService: UserService
) {
    route("/news") {
        getAllNews(newsService, userService)
        createNews(newsService, userService)
        getNews(newsService, userService)
        deleteNews(newsService, userService)
    }
}