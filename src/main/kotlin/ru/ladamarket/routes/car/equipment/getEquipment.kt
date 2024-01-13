package ru.ladamarket.routes.car.equipment

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.body.BodyService
import ru.ladamarket.database.services.carServices.carModel.CarModelService
import ru.ladamarket.database.services.carServices.engine.EngineService
import ru.ladamarket.database.services.carServices.equipment.EquipmentService
import ru.ladamarket.database.services.carServices.transmission.TransmissionService
import ru.ladamarket.modelRequest.equipment.EquipmentResponse

fun Route.getEquipment(
    modelService: CarModelService,
    bodyService: BodyService,
    transmissionService: TransmissionService,
    engineService: EngineService,
    equipmentService: EquipmentService
) {
    get("/read/{id}") {
        val equipmentId = call.parameters["id"]?.toInt()
        if (equipmentService.isEquipmentExists(equipmentId!!)) {
            val equipment = equipmentService.read(equipmentId)
            val model = modelService.read(equipment!!.modelId)
            val body = bodyService.read(equipment.bodyId)
            val transmission = transmissionService.read(equipment.transmissionId)
            val engine = engineService.read(equipment.engineId)
            if (equipment != null && model != null && body != null && transmission != null && engine != null) {
                call.respond(
                    HttpStatusCode.OK,
                    EquipmentResponse(
                        equipmentId = equipment.equipmentId,
                        equipmentName = equipment.name,
                        modelId = model.modelId,
                        modelName = model.modelName,
                        generation = model.generation,
                        country = model.country,
                        wheel = model.wheel,
                        bodyId = body.bodyId.value,
                        bodyName = body.name,
                        length = body.length,
                        width = body.width,
                        height = body.height,
                        wheelbase = body.wheelbase,
                        clearance = body.clearance,
                        seatsCount = body.seatsCount,
                        trunkVolume = body.trunkVolume,
                        tankVolume = body.tankVolume,
                        weight = body.weight,
                        maxWeight = body.maxWeight,
                        transmissionId = transmission.transmissionId.value,
                        transmissionType = transmission.type,
                        drive = transmission.drive,
                        transmissionCount = transmission.transmissionCount,
                        engineId = engine.engineId,
                        engineType = engine.type,
                        cylindersCount = engine.cylindersCount,
                        valveCount = engine.valveCount,
                        displacement = engine.displacement,
                        fuelType = engine.fuelType,
                        power = engine.power,
                        torque = engine.torque,
                        ecoClass = engine.ecoClass
                    )
                )
                return@get
            } else {
                call.respond(
                    HttpStatusCode.Conflict,
                    message = "Something wrong"
                )
                return@get
            }
        } else {
            call.respond(
                HttpStatusCode.NoContent,
                message = "No equipments with this id"
            )
            return@get
        }
    }
}