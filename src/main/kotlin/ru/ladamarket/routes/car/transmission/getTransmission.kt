package ru.ladamarket.routes.car.transmission

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.transmission.TransmissionService
import ru.ladamarket.modelRequest.transmission.transmissionResponse

fun Route.getTransmission(
    transmissionService: TransmissionService
) {
    get("/read/{id}") {
        val id = call.parameters["id"]?.toInt()
        if (transmissionService.isTransmissionExists(id!!)) {
            val transmission = transmissionService.read(id)
            if (transmission!=null) {
                call.respond(
                    HttpStatusCode.OK,
                    transmissionResponse(
                        transmissionId = id,
                        type = transmission.type,
                        drive = transmission.drive,
                        transmissionCount = transmission.transmissionCount
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.NoContent,
                    message = "No models with this id"
                )
                return@get
            }
        } else {
            call.respond(
                HttpStatusCode.NoContent,
                message = "No models with this id"
            )
            return@get
        }
    }
}