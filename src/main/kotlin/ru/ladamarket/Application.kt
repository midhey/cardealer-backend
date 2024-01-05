package ru.ladamarket

import io.ktor.server.application.*
import ru.ladamarket.plugins.*
import ru.ladamarket.security.token.TokenConfig

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val config = TokenConfig(
        audience = environment.config.property("jwt.audience").getString(),
        issuer = environment.config.property("jwt.issuer").getString(),
        expiresIn = 3L * 1000L * 60L * 60L * 24L,
        secret = environment.config.property("jwt.secret").getString()
    )
    configureKoin()
    configureSecurity(config)
    configureRouting(config)
    configureMonitoring()
    configureSerialization()
//    connectDataBase()

}
