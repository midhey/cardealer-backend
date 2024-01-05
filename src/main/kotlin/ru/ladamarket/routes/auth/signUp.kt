package ru.ladamarket.routes.auth

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.models.user.UserService
import ru.ladamarket.models.user.UserDTO
import ru.ladamarket.modelRequest.auth.signUp.RegisterRequest
import ru.ladamarket.modelRequest.auth.signUp.RegisterResponse
import ru.ladamarket.security.hash.SHA256HashingService

fun Route.signUp(
    hashingService: SHA256HashingService
) {
    post("/signup") {
        val request = call.receive<RegisterRequest>()

        val areFieldsBlank =
                request.surname.isBlank() ||
                request.name.isBlank()
                request.patronymic.isBlank() ||
                request.email.isBlank() ||
                request.phone.isBlank() ||
                request.hash.isBlank()


        val isPwdTooShort = request.hash.length < 8

        if (areFieldsBlank || isPwdTooShort) {
            call.respond(HttpStatusCode.Conflict, "pwd<8")
            return@post
        }

        val saltedHash = hashingService.generateSaltedHash(request.hash)
        UserDTO(
            surname = request.surname,
            name = request.name,
            patronymic = request.patronymic,
            email = request.email,
            phone = request.phone,
            hash = saltedHash.hash,
            salt = saltedHash.salt,
            avatar = request.avatar,
            role = request.role
        ).let { user ->
            UserService.fetchUserByEmail(request.email)?.let { _ ->
                call.respond(
                    RegisterResponse(
                        successful = false,
                        message = "email_exist"
                    )
                )

            } ?: kotlin.run {
                UserService.fetchUserByPhone(request.phone)?.let { _ ->
                    call.respond(
                        RegisterResponse(
                            successful = false,
                            message = "phone_exist"
                        )
                    )
                } ?: kotlin.run {
                    UserService.insert(user)
                }
            }
        }
        call.respond(
            HttpStatusCode.OK, RegisterResponse(
                successful = true,
                message = "succesed"
            )
        )
    }


}