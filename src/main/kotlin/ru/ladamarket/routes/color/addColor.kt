package ru.ladamarket.routes.color

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.color.ColorService
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.modelRequest.color.ColorRequest

fun Route.addColor(
    colorService: ColorService,
    userService: UserService
) {
    authenticate("jwt-auth") {
        post("/add") {
            val principal = call.principal<JWTPrincipal>()
            if (principal != null) {
                val userId = principal!!.payload.getClaim("id").asInt()
                val request = call.receive<ColorRequest>()


                if (userId != null) {
                    if (userService.isAdmin(userId)) {
                        colorService.colorAdd(request)
                        call.respond(HttpStatusCode.OK, "Color created")
                    } else {
                        //Обработка ситуации, когда запрос делает не администратор
                        call.respond(HttpStatusCode.Forbidden, "You have no rights ")
                        return@post
                    }

                }
                else {
                    call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
                    return@post
                }
            } else {
                //Обработка ситуации, когда principal равен null
                call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
                return@post
            }
        }
    }
}