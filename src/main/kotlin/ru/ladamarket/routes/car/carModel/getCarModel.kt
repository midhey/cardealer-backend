package ru.ladamarket.routes.car.carModel

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.carModel.CarModelService
import ru.ladamarket.database.services.carServices.equipment.EquipmentService
import ru.ladamarket.modelRequest.carModel.CarModelResponse

fun Route.getCarModel(
    carModelService: CarModelService,
    equipmentService: EquipmentService
) {
    get("/read/{id}") {
        val id = call.parameters["id"]?.toIntOrNull()
        if (id != null) {
            val carModel = carModelService.read(id)
            val carModelCost = equipmentService.minCostForModel(carModel!!.modelId)!!.toInt()

            if (carModel != null && carModelCost != null) {
                call.respond(
                    HttpStatusCode.OK,
                    CarModelResponse(
                        carModelId = carModel.modelId,
                        modelName = carModel.modelName,
                        generation = carModel.generation,
                        country = carModel.country,
                        wheel = carModel.wheel,
                        cost = carModelCost
                    )
                )
            } else {
                call.respond(HttpStatusCode.NoContent, "No models with this or cost is null")
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, "Invalid id parameter")
        }
    }
}
