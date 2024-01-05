package ru.ladamarket.routes.auth

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.checkToken() {
    authenticate("jwt-auth") {
        get("/check") {
            call.respond(HttpStatusCode.OK)
        }
    }
}