package ru.ladamarket

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ladamarket.database.connectDataBaseRouting
import ru.ladamarket.plugins.*
import ru.ladamarket.security.hash.SHA256HashingService
import ru.ladamarket.security.token.JwtTokenService
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
    val hashingService = SHA256HashingService()
    val tokenService = JwtTokenService()
    configureSecurity(config)
    configureRouting(
        hashingService,
        tokenService,
        config
    )
    configureMonitoring()
    configureSerialization()
    configureDatabases()
    connectDataBaseRouting()

}
