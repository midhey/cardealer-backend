package ru.ladamarket.routes.news

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.news.NewsService
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.modelRequest.news.NewsRequest

fun Route.createNews(
    newsService: NewsService,
    userService: UserService
) {
    authenticate("jwt-auth") {
        post("/create") {
            val principal = call.principal<JWTPrincipal>()
            if (principal != null) {
                val userId = principal!!.payload.getClaim("id").asInt()
                val request = call.receive<NewsRequest>()

                if (userId != null) {
                    if (userService.isAdmin(userId) || userService.isManager(userId)) {
                        newsService.create(request, userId)
                        call.respond(
                            HttpStatusCode.OK,
                            message = "News created"
                        )
                    }
                }
            }
        }
    }
}