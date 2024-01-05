package ru.ladamarket.routes.user

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.models.user.UserService
import ru.ladamarket.modelRequest.user.UserInfoResponse

fun Route.profile() {
    authenticate("jwt-auth") {
        get("/profile"){
            val principal = call.principal<JWTPrincipal>()
            if (principal != null) {
                val userId = principal!!.payload.getClaim("id").asInt()

                if (userId != null) {
                    val user = UserService.fetchUserById(userId)
                    call.respond(
                        HttpStatusCode.OK, UserInfoResponse(
                            surname = user!!.surname,
                            name = user.name,
                            patronymic = user.patronymic,
                            phone = user.patronymic,
                            email = user.email,
                            avatar = user.avatar
                    )
                    )
                }
            } else {
                //Обработка ситуации, когда principal равен null
                call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
            }
        }
    }
}