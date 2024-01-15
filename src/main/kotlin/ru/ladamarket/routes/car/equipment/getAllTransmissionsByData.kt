package ru.ladamarket.routes.car.equipment

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.equipment.EquipmentService
import ru.ladamarket.database.services.carServices.transmission.TransmissionService
import ru.ladamarket.modelRequest.body.BodyResponse
import ru.ladamarket.modelRequest.transmission.TransmissionResponse

fun Route.getAllTransmissionsByData(
    transmissionService: TransmissionService,
    equipmentService: EquipmentService
) {
    get("/read-transmissions/{modelId}/{bodyId}") {
        val modelId = call.parameters["modelId"]?.toIntOrNull()
        val bodyId = call.parameters["bodyId"]?.toIntOrNull()

        if (modelId != null && bodyId != null) {
            val equipments = equipmentService.readAllTransmissionsByData(modelId, bodyId)

            if (equipments.isNotEmpty()) {
                val transmissionResponses = equipments.map { equipment ->
                    val transmission = transmissionService.read(equipment)

                    if (transmission != null) {
                        TransmissionResponse(
                            transmissionId = transmission.transmissionId.value,
                            type = transmission.type,
                            drive = transmission.drive,
                            transmissionCount = transmission.transmissionCount
                        )
                    } else {
                        // Обработка ситуации, когда хотя бы один из объектов null
                        call.respond(HttpStatusCode.BadRequest, "Invalid equipment data")
                        return@get
                    }
                }

                call.respond(HttpStatusCode.OK, transmissionResponses)
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