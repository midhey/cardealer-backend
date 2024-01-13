package ru.ladamarket.routes.car.transmission

import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.transmission.TransmissionService

fun Route.transmission(
    transmissionService: TransmissionService
){
    route("/transmission") {
        getTransmission(transmissionService)
        getAllTransmissions(transmissionService)
    }
}