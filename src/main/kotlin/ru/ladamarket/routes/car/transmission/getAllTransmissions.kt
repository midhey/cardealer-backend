package ru.ladamarket.routes.car.transmission

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.transmission.TransmissionService
import ru.ladamarket.modelRequest.transmission.TransmissionResponse

fun Route.getAllTransmissions(
    transmissionService: TransmissionService
) {
    get("/read-all") {
        val transmissions = transmissionService.readAll()
        call.respond(
            HttpStatusCode.OK,
            transmissions.map {
                TransmissionResponse(
                    transmissionId = it.transmissionId.value,
                    type = it.type,
                    drive = it.drive,
                    transmissionCount = it.transmissionCount
                )
            }
        )
        return@get
    }
}