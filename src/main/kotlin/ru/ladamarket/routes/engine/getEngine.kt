import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.engine.EngineService
import ru.ladamarket.modelRequest.engine.engineResponse

fun Route.getEngine(
    engineService: EngineService
) {
    get("/read/{engineId}") {
        val engineId = call.parameters["engineId"]?.toShort()
        if(engineService.isEngineExist(engineId!!)) {
            val engine = engineService.read(engineId)
            if (engine!=null) {
                call.respond(
                    HttpStatusCode.OK,
                    engineResponse(
                        engineId = engine.engineId,
                        type = engine.type,
                        cylindersCount = engine.cylindersCount,
                        valveCount = engine.valveCount,
                        displacement = engine.displacement,
                        fuelType = engine.fuelType,
                        power = engine.power,
                        torque = engine.torque,
                        ecoClass = engine.ecoClass
                    )
                )
            }
            else {
                call.respond(
                    HttpStatusCode.NoContent,
                    message = "No engine with this id"
                )
            }
        }
        else {
            call.respond(
                HttpStatusCode.NoContent,
                message = "No engine with this id"
            )
        }
    }
}

