package ru.ladamarket.routes.auth

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.models.user.UserService
import ru.ladamarket.modelRequest.auth.signIn.AuthRequest
import ru.ladamarket.modelRequest.auth.signIn.AuthResponse
import ru.ladamarket.security.hash.SHA256HashingService
import ru.ladamarket.security.hash.SaltedHash
import ru.ladamarket.security.token.TokenClaim
import ru.ladamarket.security.token.TokenConfig
import ru.ladamarket.security.token.TokenService
fun Route.signIn(
    hashingService: SHA256HashingService,
    tokenService: TokenService,
    config: TokenConfig,
) {
    post("/signin") {
        val request = call.receive<AuthRequest>()

        val user = if (request.phone.equals(null)) UserService.fetchUserByEmail(request.email) else UserService.fetchUserByPhone(request.phone)

        if (user == null) {
            call.respond(HttpStatusCode.Conflict, "Incorrect email")
            return@post
        }

        val isValidPassword = hashingService.verify(
            value = request.hash,
            saltedHash = SaltedHash(
                hash = user.hash,
                salt = user.salt
            )
        )

        if (!isValidPassword) {
            call.respond(HttpStatusCode.Conflict, "Incorrect password")
            return@post
        }

        val userId = UserService.getUserIdByEmail(user.email)

        val token = tokenService.generateToken(
            config = config,
            TokenClaim(
                name = "id",
                value = userId
            )
        )

        call.respond(HttpStatusCode.OK,
            AuthResponse(
                token = token,
                message = "success"
            )
        )
    }
}