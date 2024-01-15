package ru.ladamarket.routes.car.colorToModel

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.colorToModel.ColorToModelService
import ru.ladamarket.database.services.color.ColorService
import ru.ladamarket.modelRequest.body.BodyResponse
import ru.ladamarket.modelRequest.color.ColorResponse
import ru.ladamarket.modelRequest.colorToModel.ColorToModelResponse
import ru.ladamarket.models.carModels.ColorToModel

fun Route.colorToModel(
    colorToModelService: ColorToModelService,
    colorService: ColorService
) {
    route("/color-list") {
        get("/read-all/{modelId}") {
            val modelId = call.parameters["modelId"]?.toIntOrNull()
            if (modelId!=null) {
                val colors = colorToModelService.readAllByModel(modelId)
                if (colors.isNotEmpty()) {
                    val colorResponses = colors.map { color ->
                        val colorInfo = colorService.read(color)
                        if (colorInfo!=null)  {
                            ColorResponse(
                                colorName = colorInfo.colorName,
                                colorCode = colorInfo.colorCode,
                                colorHex = colorInfo.colorHex
                            )
                        } else {
                            call.respond(HttpStatusCode.BadRequest, "Invalid color data")
                            return@get
                        }
                    }
                    call.respond(
                        HttpStatusCode.OK,
                        colorResponses
                    )
                } else {
                    call.respond(
                        HttpStatusCode.Conflict
                    )
                }

            } else {
                call.respond(
                    HttpStatusCode.NoContent,
                    message = "Model id is not exist"
                )
                return@get
            }
        }
    }
}