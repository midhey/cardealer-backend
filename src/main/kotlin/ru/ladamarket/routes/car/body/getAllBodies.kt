package ru.ladamarket.routes.car.body

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.body.BodyService
import ru.ladamarket.modelRequest.body.BodyResponse

fun Route.getAllBodies(
    bodyService: BodyService
) {
    get("/read-all") {
        val bodies = bodyService.readAll()
        call.respond(
            HttpStatusCode.OK,
            bodies.map {
                BodyResponse(
                    bodyId = it.bodyId.value,
                    name = it.name,
                    length = it.length,
                    width = it.width,
                    height = it.height,
                    wheelbase = it.wheelbase,
                    clearance = it.clearance,
                    seatsCount = it.seatsCount,
                    trunkVolume = it.trunkVolume,
                    tankVolume = it.tankVolume,
                    weight = it.weight,
                    maxWeight = it.maxWeight
                )
            }
        )
        return@get
    }
}