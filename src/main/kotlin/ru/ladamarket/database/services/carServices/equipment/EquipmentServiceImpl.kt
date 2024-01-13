package ru.ladamarket.database.services.carServices.equipment

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ladamarket.database.services.carServices.equipment.EquipmentServiceImpl.EquipmentTable.bodyId
import ru.ladamarket.database.services.carServices.equipment.EquipmentServiceImpl.EquipmentTable.cost
import ru.ladamarket.database.services.carServices.equipment.EquipmentServiceImpl.EquipmentTable.engineId
import ru.ladamarket.database.services.carServices.equipment.EquipmentServiceImpl.EquipmentTable.equipmentName
import ru.ladamarket.database.services.carServices.equipment.EquipmentServiceImpl.EquipmentTable.modelId
import ru.ladamarket.database.services.carServices.equipment.EquipmentServiceImpl.EquipmentTable.transmissionId
import ru.ladamarket.models.carModels.Equipment

class EquipmentServiceImpl(database: Database): EquipmentService {

    object EquipmentTable:IntIdTable("equipment") {
        val equipmentName = varchar("name",30)
        val modelId = integer("model_id")
        val bodyId = integer("body_id")
        val transmissionId = integer("transmission_id")
        val engineId = integer("engine_id")
        val cost = double("cost")
    }

    init {
        transaction(database) {
            SchemaUtils.create(EquipmentTable)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T) = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }
    override suspend fun read(id: Int): Equipment? {
        return dbQuery {
            EquipmentTable.select(where = {EquipmentTable.id eq id}).singleOrNull()?.let { ResultRowToEquipment(it) }
        }
    }

    override suspend fun readAll(): List<Equipment> {
        return dbQuery {
            EquipmentTable.selectAll().map { ResultRowToEquipment(it) }
        }
    }

    override suspend fun isEquipmentExists(id: Int): Boolean {
        return dbQuery {
            EquipmentTable.select(where = {EquipmentTable.id eq id}).count() > 0
        }
    }

    private fun ResultRowToEquipment(row: ResultRow) = Equipment(
        equipmentId = row[EquipmentTable.id].value,
        name = row[equipmentName],
        modelId = row[modelId],
        bodyId = row[bodyId],
        transmissionId = row[transmissionId],
        engineId = row[engineId],
        cost = row[cost]
    )
}