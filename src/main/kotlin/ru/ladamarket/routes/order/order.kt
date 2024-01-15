package ru.ladamarket.routes.order

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

fun Route.order(
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
    route("/order") {
        createOrder(
            orderService,
            userService
        )
        getAllUserOrders(
            orderService,
            userService,
            modelService,
            bodyService,
            transmissionService,
            engineService,
            equipmentService,
            colorToModelService,
            colorService
        )
        getAllOrders(
            orderService,
            userService,
            modelService,
            bodyService,
            transmissionService,
            engineService,
            equipmentService,
            colorToModelService,
            colorService
        )
        setStatus(userService,orderService)
    }
}