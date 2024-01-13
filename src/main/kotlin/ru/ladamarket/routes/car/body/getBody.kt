package ru.ladamarket.routes.car.body

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.body.BodyService
import ru.ladamarket.modelRequest.body.bodyResponse

fun Route.getBody(
    bodyService: BodyService
) {
    get("/read/{id}") {
        val id = call.parameters["id"]?.toInt()
        if (bodyService.isBodyExist(id!!)) {
            val body = bodyService.read(id)
            if (body!=null) {
                call.respond(
                    HttpStatusCode.OK,
                    bodyResponse(
                        bodyId = id,
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
                )
                return@get
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