package ru.ladamarket.plugins


import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.routes.auth.auth
import ru.ladamarket.routes.user.profile
import ru.ladamarket.security.hash.HashingService
import ru.ladamarket.security.token.TokenConfig
import ru.ladamarket.security.token.TokenService

fun Application.configureRouting(config: TokenConfig) {
    install(AutoHeadResponse)
    val userService: UserService by inject()
    val hashingService: HashingService by inject()
    val tokenService: TokenService by inject()

    routing {
        auth(
            userService = userService,
            tokenService = tokenService,
            hashingService = hashingService,
            tokenConfig = config
        )
        profile(
            userService = userService,
            hashingService = hashingService
        )

    }
}

//fun Application.configureRouting(
//    val userService: SHA256HashingService by inject()
//    val hashingService: HashingService by inject()
//    val tokenService: TokenService by inject()
//) {
//    install(AutoHeadResponse)
//        routing {
//            auth(
//                userService = userService,
//                tokenService = tokenService,
//                hashingService = hashingService,
//                tokenConfig = config
//            )
////        signUp(hashingService)
////        signIn(hashingService, tokenService, config)
////        profile()
//        }
//}
