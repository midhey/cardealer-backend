package ru.ladamarket.routes.admin

import io.ktor.server.routing.*
import ru.ladamarket.database.services.user.UserService

fun Route.admin(
    userService: UserService
) {
    route("/admin") {
        getUsersList(userService)
    }
}