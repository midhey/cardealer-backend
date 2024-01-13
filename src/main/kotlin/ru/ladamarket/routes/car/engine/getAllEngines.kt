package ru.ladamarket.routes.car.engine

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.engine.EngineService
import ru.ladamarket.modelRequest.engine.engineResponse

fun Route.getAllEngines(
    engineService: EngineService
) {
    get("/read-all") {
        val engines = engineService.readAll()
        call.respond(
            HttpStatusCode.OK,
            engines.map {
                engineResponse (
                    engineId = it.engineId,
                    type = it.type,
                    cylindersCount = it.cylindersCount,
                    valveCount = it.valveCount,
                    displacement = it.displacement,
                    fuelType = it.fuelType,
                    power = it.power,
                    torque = it.torque,
                    ecoClass = it.ecoClass
                )
            }
        )
        return@get
    }
}