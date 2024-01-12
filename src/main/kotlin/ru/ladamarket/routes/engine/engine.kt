package ru.ladamarket.routes.engine

import getEngine
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.engine.EngineService

fun Route.engine(
    engineService: EngineService
) {
    route("/engine") {
        getEngine(engineService)
        getAllEngines(engineService)
    }
}