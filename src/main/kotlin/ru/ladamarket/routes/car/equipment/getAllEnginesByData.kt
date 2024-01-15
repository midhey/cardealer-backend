package ru.ladamarket.routes.car.equipment

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.engine.EngineService
import ru.ladamarket.database.services.carServices.equipment.EquipmentService
import ru.ladamarket.modelRequest.engine.EngineResponse
import ru.ladamarket.modelRequest.transmission.TransmissionResponse

fun Route.getAllEnginesByData (
    engineService: EngineService,
    equipmentService: EquipmentService
) {
    get("/read-engines/{modelId}/{bodyId}/{transmissionId}") {
        val modelId = call.parameters["modelId"]?.toIntOrNull()
        val bodyId = call.parameters["bodyId"]?.toIntOrNull()
        val transmissionId = call.parameters["transmissionId"]?.toIntOrNull()

        if (modelId != null && bodyId!=null && transmissionId !=null) {
            val equipments = equipmentService.readAllEnginesByData(modelId, bodyId, transmissionId)

            if (equipments.isNotEmpty()) {
                val engineResponses = equipments.map { equipment ->
                    val engine = engineService.read(equipment)

                    if (engine != null) {
                        EngineResponse(
                            engineId = engine.engineId,
                            type = engine.type,
                            cylindersCount = engine.cylindersCount,
                            valveCount = engine.valveCount,
                            displacement = engine.displacement,
                            fuelType = engine.fuelType,
                            power = engine.power,
                            torque = engine.torque,
                            ecoClass = engine.ecoClass
                        )
                    } else {
                        // Обработка ситуации, когда хотя бы один из объектов null
                        call.respond(HttpStatusCode.BadRequest, "Invalid equipment data")
                        return@get
                    }
                }

                call.respond(HttpStatusCode.OK, engineResponses)
            } else {
                call.respond(
                    HttpStatusCode.NoContent,
                    message = "No bodies with this model id"
                )
            }
        } else {
            call.respond(
                HttpStatusCode.BadRequest,
                message = "Invalid model id"
            )
        }
    }
}