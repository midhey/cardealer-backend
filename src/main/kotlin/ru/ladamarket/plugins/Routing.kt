package ru.ladamarket.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.routes.auth.signIn
import ru.ladamarket.routes.auth.signUp
import ru.ladamarket.routes.user.profile
import ru.ladamarket.security.hash.SHA256HashingService
import ru.ladamarket.security.token.JwtTokenService
import ru.ladamarket.security.token.TokenConfig

fun Application.configureRouting(
    hashingService: SHA256HashingService,
    tokenService: JwtTokenService,
    config: TokenConfig
) {
    install(AutoHeadResponse)
    routing {
        signUp(hashingService)
        signIn(hashingService, tokenService, config)
        profile()
    }
}
