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

fun Route.getAllUserOrders(
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
        get("/read") {
            val principal = call.principal<JWTPrincipal>()
            if (principal != null) {
                val userId = principal!!.payload.getClaim("id").asInt()

                if (userId != null) {
                    val orders = orderService.readAllOrdersByUser(userId)
                    println(orders)
                    val user = userService.read(userId)

                    if (orders.isNotEmpty()) {
                        val ordersResponses = orders.mapNotNull { order ->
                            val equipment = equipmentService.read(order.equipmentId)
                            println(equipment)
                            val model = equipment?.let { modelService.read(it.modelId) }
                            println(model)
                            val body = equipment?.let { bodyService.read(it.bodyId) }
                            println(body)
                            val transmission = equipment?.let { transmissionService.read(it.transmissionId) }
                            println(transmission)
                            val engine = equipment?.let { engineService.read(it.engineId) }
                            println(engine)
                            val colorToModel = colorToModelService.read(order.colorId)
                            println(colorToModel)
                            val color = colorToModel?.let { colorService!!.read(it.colorCode) }
                            println(color)

                            model?.let { nonNullModel ->
                                body?.let { nonNullBody ->
                                    transmission?.let { nonNullTransmission ->
                                        engine?.let { nonNullEngine ->
                                            color?.let { nonNullColor ->
                                                OrderResponse(
                                                    orderId = order.orderId,
                                                    equipmentId = order.equipmentId,
                                                    modelName = nonNullModel.modelName,
                                                    bodyName = nonNullBody.name,
                                                    equipmentName = equipment?.name.orEmpty(),
                                                    colorName = nonNullColor.colorName,
                                                    colorHex = nonNullColor.colorHex,
                                                    userId = userId,
                                                    userPhone = user?.phone.orEmpty(),
                                                    userMail = user?.email.orEmpty(),
                                                    status = order.status,
                                                    orderTime = order.orderTime.toString()
                                                )
                                            }
                                        }
                                    }
                                }
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
                    call.respond(HttpStatusCode.Unauthorized, "Unauthorized")

                }
            } else {
                //Обработка ситуации, когда principal равен null
                call.respond(HttpStatusCode.Unauthorized, "Unauthorized")

            }
        }
    }
}