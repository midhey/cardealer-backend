package ru.ladamarket.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import ru.ladamarket.security.token.TokenConfig

fun Application.configureSecurity(config: TokenConfig) {
    install(Authentication) {
        jwt("jwt-auth") {
            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(config.audience)){
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }
}
