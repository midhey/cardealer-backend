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

fun Route.updateColor(
    colorService: ColorService,
    userService: UserService
) {
    authenticate("jwt-auth") {
        post("/update") {
            val principal = call.principal<JWTPrincipal>()
            if (principal != null) {
                val userId = principal!!.payload.getClaim("id").asInt()
                val request = call.receive<ColorRequest>()

                if (!colorService.isColorExist(request.colorCode)) {
                    //Обработка ситуации, когда цвета не существует
                    call.respond(HttpStatusCode.Conflict, "Color do not exist")
                    return@post
                }
                if (userId != null) {
                    if (userService.isAdmin(userId)) {
                        colorService.colorUpdate(request)
                        call.respond(
                            HttpStatusCode.OK,
                            "Color updated"
                        )
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