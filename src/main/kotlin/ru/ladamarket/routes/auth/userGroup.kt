package ru.ladamarket.routes.auth

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.modelRequest.user.UserGroup

fun Route.userGroup(
    userService: UserService
) {
    authenticate("jwt-auth") {
        get("/check-group"){
            val principal = call.principal<JWTPrincipal>()

            if (principal!=null) {
                val userId = principal!!.payload.getClaim("id").asInt()
                if (userService.isAdmin(userId)) {
                    call.respond(
                        HttpStatusCode.OK,
                        UserGroup(
                            "Администратор"
                        )
                    )
                } else if(userService.isManager(userId)) {
                    call.respond(
                        HttpStatusCode.OK,
                        UserGroup(
                            "Менеджер"
                        )
                    )
                } else {
                    call.respond(
                        HttpStatusCode.OK,
                        UserGroup(
                            "Пользователь"
                        )
                    )
                }
            } else {
                call.respond(
                    HttpStatusCode.Unauthorized
                )
            }
        }
    }
}