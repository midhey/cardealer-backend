package ru.ladamarket.routes.user

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.modelRequest.user.UserInfoUpdate
import ru.ladamarket.security.hash.HashingService
import ru.ladamarket.security.hash.SaltedHash

fun Route.updateUser(
    userService: UserService,
    hashingService: HashingService
) {
    authenticate("jwt-auth") {
        post("/update") {
            val principal = call.principal<JWTPrincipal>()
            val request = call.receive<UserInfoUpdate>()

            principal?.let {
                val userId = it.payload.getClaim("id").asInt()

                if (userId != null) {
                    val user = userService.read(userId)
                    user ?: call.respond(HttpStatusCode.NotFound, "User not found")

                    var saltedHash = SaltedHash(
                        hash = user!!.hash,
                        salt = user.salt
                    )

                    if (request.hash != null) {
                        saltedHash = hashingService.generateSaltedHash(request.hash)
                    }

                    userService.update(userId, request, saltedHash)
                    call.respond(HttpStatusCode.OK, "User updated")
                    return@post
                }
            }
        }
    }
}
