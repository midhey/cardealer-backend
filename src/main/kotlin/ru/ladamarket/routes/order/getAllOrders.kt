package ru.ladamarket.routes.order

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.body.BodyService
import ru.ladamarket.database.services.carServices.carModel.CarModelService
import ru.ladamarket.database.services.carServices.colorToModel.ColorToModelService
import ru.ladamarket.database.services.carServices.engine.EngineService
import ru.ladamarket.database.services.carServices.equipment.EquipmentService
import ru.ladamarket.database.services.carServices.transmission.TransmissionService
import ru.ladamarket.database.services.color.ColorService
import ru.ladamarket.database.services.order.OrderService
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.modelRequest.order.OrderResponse

fun Route.getAllOrders(
    orderService: OrderService,
    userService: UserService,
    modelService: CarModelService,
    bodyService: BodyService,
    transmissionService: TransmissionService,
    engineService: EngineService,
    equipmentService: EquipmentService,
    colorToModelService: ColorToModelService,
    colorService: ColorService
) {
    authenticate("jwt-auth") {
        get("/read-all") {
            val principal = call.principal<JWTPrincipal>()
            if (principal != null) {
                val userId = principal!!.payload.getClaim("id").asInt()

                if (userId != null) {
                    if(userService.isAdmin(userId) || userService.isManager(userId)) {
                        val orders = orderService.readAll()


                        if (orders.isNotEmpty()) {
                            val ordersResponses = orders.map { order ->
                                val user = userService.read(order.userId)
                                val equipment = equipmentService.read(order.equipmentId)
                                val model = modelService.read(equipment!!.modelId)
                                val body = bodyService.read(equipment.bodyId)
                                val transmission = transmissionService.read(equipment.transmissionId)
                                val engine = engineService.read(equipment.engineId)
                                val colorToModel = colorToModelService.read(order.colorId)
                                val color = colorService.read(colorToModel!!.colorCode)

                                if (
                                    user != null
                                    && model != null
                                    && body != null
                                    && transmission != null
                                    && engine != null
                                    && color != null
                                ) {

                                    OrderResponse(
                                        orderId = order.orderId,
                                        equipmentId = order.equipmentId,
                                        modelName = model.modelName,
                                        bodyName = body.name,
                                        equipmentName = equipment.name,
                                        colorName = color.colorName,
                                        colorHex = color.colorHex,
                                        userId = userId,
                                        userPhone = user!!.phone,
                                        userMail = user.email,
                                        status = order.status,
                                        orderTime = order.orderTime.toString()
                                    )
                                } else {
                                    println("Skipping order ${order.orderId} due to missing data.")
                                    null
                                }
                            }
                            call.respond(
                                HttpStatusCode.OK,
                                ordersResponses
                            )
                        } else {
                            call.respond(HttpStatusCode.NoContent)
                        }

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