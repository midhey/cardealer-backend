package ru.ladamarket.routes.color

import io.ktor.server.routing.*
import ru.ladamarket.database.services.color.ColorService
import ru.ladamarket.database.services.user.UserService

fun Route.color(
    colorService: ColorService,
    userService: UserService
) {
    route("/color") {
        getAllColors(colorService,userService)
        updateColor(colorService,userService)
        addColor(colorService,userService)
        deleteColor(colorService, userService)
    }
}