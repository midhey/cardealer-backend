package ru.ladamarket.routes.car.carModel

import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.carModel.CarModelService

fun Route.carModel(
    carModelService: CarModelService
) {
    route("/models") {
        getCarModel(carModelService)
        getAllCarModels(carModelService)
    }
}