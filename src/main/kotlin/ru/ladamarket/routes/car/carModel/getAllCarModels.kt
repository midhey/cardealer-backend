package ru.ladamarket.routes.car.carModel

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.carModel.CarModelService
import ru.ladamarket.modelRequest.carModel.carModelResponse

fun Route.getAllCarModels(
    carModelService: CarModelService
) {
    get("/read-all") {
        val carModels = carModelService.readAll()
        call.respond(
            HttpStatusCode.OK,
            carModels.map {
                carModelResponse(
                    carModelId = it.modelId,
                    modelName = it.modelName,
                    generation = it.generation,
                    country = it.country,
                    wheel = it.wheel
                )
            }
        )
        return@get
    }
}
