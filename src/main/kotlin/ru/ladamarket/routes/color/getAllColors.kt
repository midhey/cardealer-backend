package ru.ladamarket.routes.color

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.color.ColorService
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.modelRequest.color.ColorResponse

fun Route.getAllColors(
    colorService: ColorService,
    userService: UserService
) {
    authenticate("jwt-auth") {
        get("/get-all") {
            val principal = call.principal<JWTPrincipal>()
            if (principal != null) {
                val userId = principal!!.payload.getClaim("id").asInt()

                if (userId != null) {
                    val colors = colorService.readAll()
                    if (userService.isAdmin(userId)||userService.isManager(userId)) {
                        call.respond(
                            HttpStatusCode.OK,
                            colors.map {
                                ColorResponse(
                                    colorName = it.colorName,
                                    colorCode = it.colorCode,
                                    colorHex = it.colorHex
                                )
                            }
                        )
                    } else {
                        //Обработка ситуации, когда запрос делает не администратор
                        call.respond(HttpStatusCode.Forbidden, "You have no rights ")
                        return@get
                    }

                }
                else {
                    call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
                    return@get
                }
            } else {
                //Обработка ситуации, когда principal равен null
                call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
                return@get
            }
        }
    }
}