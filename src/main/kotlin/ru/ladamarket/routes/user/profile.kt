package ru.ladamarket.routes.user

import io.ktor.server.routing.*
import ru.ladamarket.database.services.user.UserService

fun Route.profile(
    userService: UserService
) {
    route("/profile") {
        getUser(
            userService = userService
        )
    }
}