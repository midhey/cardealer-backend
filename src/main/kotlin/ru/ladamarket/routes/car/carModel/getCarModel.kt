package ru.ladamarket.routes.car.carModel

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.carModel.CarModelService
import ru.ladamarket.modelRequest.carModel.carModelResponse

fun Route.getCarModel(
    carModelService: CarModelService
) {
    get("/read/{id}") {
        val id = call.parameters["id"]?.toInt()
        if (carModelService.isModelExists(id!!)) {
            val carModel = carModelService.read(id)
            if (carModel != null) {
                call.respond(
                    HttpStatusCode.OK,
                    carModelResponse(
                        carModelId = carModel.modelId,
                        modelName = carModel.modelName,
                        generation = carModel.generation,
                        country = carModel.country,
                        wheel = carModel.wheel
                    )
                )
                return@get
            } else {
                call.respond(
                    HttpStatusCode.NoContent,
                    message = "No models with this id"
                )
                return@get
            }

        }
        else {
            call.respond(
                HttpStatusCode.NoContent,
                message = "No models with this id"
            )
            return@get
        }
    }
}