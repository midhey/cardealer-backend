package ru.ladamarket.routes.order

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.order.OrderService
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.modelRequest.order.OrderResponse

fun Route.setStatus(
    userService: UserService,
    orderService: OrderService
) {
    authenticate("jwt-auth") {
        get("/update-status/{orderId}/{status}") {
            val principal = call.principal<JWTPrincipal>()

            if (principal != null) {
                val userId = principal!!.payload.getClaim("id").asInt()
                val orderId = call.parameters["orderId"]?.toIntOrNull()
                if (orderId == null) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        message = "This order id not found"
                    )
                    return@get
                }

                val status = call.parameters["status"].toString()

                if (!status.equals("Отменен") && !status.equals("Принят")) {
                    call.respond(
                        HttpStatusCode.Conflict,
                        message = "This status not acceptable"
                    )
                    return@get
                }

                if (userId != null) {
                    if(userService.isAdmin(userId) || userService.isManager(userId)) {
                        orderService.setStatus(orderId, status)
                        call.respond(
                            HttpStatusCode.OK,
                            message = "Status updated"
                        )

                    } else {
                        call.respond(HttpStatusCode.Forbidden, "You have no rights")
                    }
                } else {
                    call.respond(HttpStatusCode.Unauthorized, "Unauthorized")

                }
            } else {
                //Обработка ситуации, когда principal равен null
                call.respond(HttpStatusCode.Unauthorized, "Unauthorized")

            }
        }
    }
}