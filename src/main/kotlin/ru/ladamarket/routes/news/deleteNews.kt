package ru.ladamarket.routes.news

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.news.NewsService
import ru.ladamarket.database.services.user.UserService

fun Route.deleteNews(
    newsService: NewsService,
    userService: UserService
) {
    authenticate("jwt-auth") {
        get("/delete/{newsId}") {
            val principal = call.principal<JWTPrincipal>()
            if (principal != null) {
                val userId = principal!!.payload.getClaim("id").asInt()

                if (userId != null) {
                    if (userService.isAdmin(userId) || userService.isManager(userId)) {
                        val newsId = call.parameters["newsId"]!!.toInt()
                        val news = newsService.read(newsId)
                        if (news != null) {
                            newsService.delete(newsId)
                            call.respond(
                                HttpStatusCode.OK,
                                message = "News deleted"
                            )
                        }
                    }
                }
            }
        }
    }
}