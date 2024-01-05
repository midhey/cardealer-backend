import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.modelRequest.auth.signUp.RegisterRequest
import ru.ladamarket.modelRequest.auth.signUp.RegisterResponse
import ru.ladamarket.security.hash.HashingService

fun Route.signUp(
    userService: UserService,
    hashingService: HashingService
) {
    post ("/signup"){
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


        if (userService.isEmailExist(request.email)) {
            call.respond(
                RegisterResponse(
                    successful = false,
                    message = "phone_exist"
                )
            )
            return@post
        }

        if (userService.isPhoneExist(request.phone)) {
            call.respond(
                RegisterResponse(
                    successful = false,
                    message = "phone_exist"
                )
            )
            return@post
        }

        val saltedHash = hashingService.generateSaltedHash(request.hash)
        userService.create(request, saltedHash)

        call.respond(
            HttpStatusCode.OK, RegisterResponse(
                successful = true,
                message = "succesed"
            )
        )
        return@post
    }
}