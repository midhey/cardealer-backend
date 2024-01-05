package ru.ladamarket.routes.user

import io.ktor.server.routing.*
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.security.hash.HashingService

fun Route.profile(
    userService: UserService,
    hashingService: HashingService
) {
    route("/profile") {
        getUser(
            userService = userService
        )
        updateUser(
            userService = userService,
            hashingService = hashingService
        )
    }
}