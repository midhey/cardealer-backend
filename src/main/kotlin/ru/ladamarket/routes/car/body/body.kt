package ru.ladamarket.routes.car.body

import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.body.BodyService

fun Route.body(
    bodyService: BodyService
) {
    route ("/body") {
        getBody(bodyService)
        getAllBodies(bodyService)
    }
}