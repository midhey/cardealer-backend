package ru.ladamarket.routes.car.carModel

import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.carModel.CarModelService
import ru.ladamarket.database.services.carServices.equipment.EquipmentService

fun Route.carModel(
    carModelService: CarModelService,
    equipmentService: EquipmentService
) {
    route("/models") {
        getCarModel(carModelService, equipmentService)
        getAllCarModels(carModelService, equipmentService)
    }
}