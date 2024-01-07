package ru.ladamarket.routes.admin

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.modelRequest.user.UserInfoAllResponse

fun Route.getUsersList(
    userService: UserService
) {
    authenticate("jwt-auth") {
        get("/getuserslist") {
            val principal = call.principal<JWTPrincipal>()
            if (principal != null) {
                val userId = principal!!.payload.getClaim("id").asInt()

                if (userId != null) {
                    val users = userService.readAll()
                    if (userService.isAdmin(userId)) {
                        call.respond(
                            HttpStatusCode.OK,
                            users.map {
                                UserInfoAllResponse(
                                    surname = it.surname,
                                    name = it.name,
                                    patronymic = it.patronymic ?: "",
                                    phone = it.phone,
                                    email = it.email,
                                    avatar = it.avatar ?: "",
                                    role = it.role
                                )
                            }
                        )
                    } else {
                        //Обработка ситуации, когда запрос делает не администратор
                        call.respond(HttpStatusCode.Forbidden, "You have no rights ")
                    }

                }
                else {
                    call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
                }
            } else {
                //Обработка ситуации, когда principal равен null
                call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
            }
        }
    }
}