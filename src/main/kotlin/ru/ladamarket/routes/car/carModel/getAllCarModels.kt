package ru.ladamarket.routes.car.carModel

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.carModel.CarModelService
import ru.ladamarket.database.services.carServices.equipment.EquipmentService
import ru.ladamarket.modelRequest.carModel.CarModelResponse

fun Route.getAllCarModels(
    carModelService: CarModelService,
    equipmentService: EquipmentService
) {
    get("/read-all") {
        val carModels = carModelService.readAll()
        call.respond(
            HttpStatusCode.OK,
            carModels.map { model->
                val cost = equipmentService.minCostForModel(model.modelId)?.toInt()
                CarModelResponse(
                    carModelId = model.modelId,
                    modelName = model.modelName,
                    generation = model.generation,
                    country = model.country,
                    wheel = model.wheel,
                    cost = cost!!
                )
            }
        )
        return@get
    }
}
