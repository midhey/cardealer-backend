package ru.ladamarket.routes.auth

import io.ktor.server.routing.*
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.security.hash.HashingService
import ru.ladamarket.security.token.TokenConfig
import ru.ladamarket.security.token.TokenService
import signIn
import signUp

fun Route.auth(
    userService: UserService,
    tokenService: TokenService,
    hashingService: HashingService,
    tokenConfig: TokenConfig
) {
    route("/auth") {
        signIn(
            userService = userService,
            tokenService = tokenService,
            hashingService = hashingService,
            tokenConfig = tokenConfig
        )
        signUp(
            userService = userService,
            hashingService = hashingService
        )
        checkToken()
    }
}



