package ru.ladamarket.routes.car.equipment

import io.ktor.server.routing.*
import ru.ladamarket.database.services.carServices.body.BodyService
import ru.ladamarket.database.services.carServices.carModel.CarModelService
import ru.ladamarket.database.services.carServices.engine.EngineService
import ru.ladamarket.database.services.carServices.equipment.EquipmentService
import ru.ladamarket.database.services.carServices.transmission.TransmissionService

fun Route.equipment(
    modelService: CarModelService,
    bodyService: BodyService,
    transmissionService: TransmissionService,
    engineService: EngineService,
    equipmentService: EquipmentService
) {
    route("/equipment") {
        getEquipment(
            modelService,
            bodyService,
            transmissionService,
            engineService,
            equipmentService
        )
        getAllEquipments(
            modelService,
            bodyService,
            transmissionService,
            engineService,
            equipmentService
        )
        getAllBodiesByModel(
            bodyService,
            equipmentService
        )
    }
}