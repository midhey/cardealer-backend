package ru.ladamarket.routes.car.equipment

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.body.BodyService
import ru.ladamarket.database.services.carServices.equipment.EquipmentService
import ru.ladamarket.modelRequest.body.BodyResponse

fun Route.getAllBodiesByModel(
    bodyService: BodyService,
    equipmentService: EquipmentService
) {
    get("/read-bodies/{id}") {
        val modelId = call.parameters["id"]?.toIntOrNull()
        if (modelId != null) {
            val equipments = equipmentService.readAllBodiesByModel(modelId)

            if (equipments.isNotEmpty()) {
                val bodyResponses = equipments.map { equipment ->
                    val body = bodyService.read(equipment)

                    if (body != null) {
                        BodyResponse(
                            bodyId = body.bodyId.value,
                            name = body.name,
                            length = body.length,
                            width = body.width,
                            height = body.height,
                            wheelbase = body.wheelbase,
                            clearance = body.clearance,
                            seatsCount = body.seatsCount,
                            trunkVolume = body.trunkVolume,
                            tankVolume = body.tankVolume,
                            weight = body.weight,
                            maxWeight = body.maxWeight
                        )
                    } else {
                        // Обработка ситуации, когда хотя бы один из объектов null
                        call.respond(HttpStatusCode.BadRequest, "Invalid equipment data")
                        return@get
                    }
                }

                call.respond(HttpStatusCode.OK, bodyResponses)
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
