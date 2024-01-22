import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.user.UserService
import ru.ladamarket.modelRequest.auth.signIn.AuthRequest
import ru.ladamarket.modelRequest.auth.signIn.AuthResponse
import ru.ladamarket.security.hash.HashingService
import ru.ladamarket.security.hash.SaltedHash
import ru.ladamarket.security.token.TokenClaim
import ru.ladamarket.security.token.TokenConfig
import ru.ladamarket.security.token.TokenService

fun Route.signIn(
    userService: UserService,
    tokenService: TokenService,
    hashingService: HashingService,
    tokenConfig: TokenConfig
) {
    post("/signin") {
        val request = call.receive<AuthRequest>()

        val user = if (request.phone == "") userService.readByEmail(request.email) else userService.readByPhone(request.phone)

        if (user == null) {
            call.respond(HttpStatusCode.Conflict, "Incorrect email or phone")
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

        val userId = userService.readIdByEmail(user.email)

        val token = tokenService.generateToken(
            config = tokenConfig,
            TokenClaim(
                name = "id",
                value = userId
            )
        )

        call.respond(
            HttpStatusCode.OK,
            AuthResponse(
                token = token,
                message = "success"
            )
        )
        return@post
    }
}