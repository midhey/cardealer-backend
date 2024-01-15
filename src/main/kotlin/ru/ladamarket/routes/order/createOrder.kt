package ru.ladamarket.routes.order

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.order.OrderService
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.modelRequest.color.ColorRequest
import ru.ladamarket.modelRequest.order.OrderRequest
import ru.ladamarket.models.orderModels.Order

fun Route.createOrder(
    orderService: OrderService,
    userService: UserService
) {
    authenticate("jwt-auth") {
        post("/create") {
            val principal = call.principal<JWTPrincipal>()
            if (principal != null) {
                val userId = principal!!.payload.getClaim("id").asInt()
                val request = call.receive<OrderRequest>()




                if (userId != null) {
                    orderService.create(request, userId)
                    call.respond(HttpStatusCode.OK, "Order created")

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