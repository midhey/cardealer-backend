package ru.ladamarket.database.services.carServices.equipment

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ladamarket.database.services.carServices.body.BodyServiceImpl
import ru.ladamarket.database.services.carServices.carModel.CarModelServiceImpl
import ru.ladamarket.database.services.carServices.engine.EngineServiceImpl
import ru.ladamarket.database.services.carServices.equipment.EquipmentServiceImpl.EquipmentTable.bodyId
import ru.ladamarket.database.services.carServices.equipment.EquipmentServiceImpl.EquipmentTable.cost
import ru.ladamarket.database.services.carServices.equipment.EquipmentServiceImpl.EquipmentTable.engineId
import ru.ladamarket.database.services.carServices.equipment.EquipmentServiceImpl.EquipmentTable.equipmentName
import ru.ladamarket.database.services.carServices.equipment.EquipmentServiceImpl.EquipmentTable.modelId
import ru.ladamarket.database.services.carServices.equipment.EquipmentServiceImpl.EquipmentTable.transmissionId
import ru.ladamarket.database.services.carServices.transmission.TransmissionServiceImpl
import ru.ladamarket.models.carModels.Equipment

class EquipmentServiceImpl(database: Database) : EquipmentService {

    object EquipmentTable : IntIdTable("equipment") {
        val equipmentName = varchar("name", 30)
        val modelId = reference("model_id", CarModelServiceImpl.CarModelTable)
        val bodyId = reference("body_id", BodyServiceImpl.BodyTable)
        val transmissionId = reference("transmission_id", TransmissionServiceImpl.TransmissionTable)
        val engineId = reference("engine_id", EngineServiceImpl.EngineTable.engineId)
        val cost = double("cost")
    }

    init {
        transaction(database) {
            SchemaUtils.create(EquipmentTable)

            if (EquipmentTable.selectAll().count().toInt() == 0) {
                //Granta sedan
                EquipmentTable.insert {
                    it[equipmentName] = "Standart"
                    it[modelId] = 1
                    it[bodyId] = 1
                    it[transmissionId] = 1
                    it[engineId] = 11182
                    it[cost] = 699900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Classic"
                    it[modelId] = 1
                    it[bodyId] = 1
                    it[transmissionId] = 1
                    it[engineId] = 11182
                    it[cost] = 799900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Comfort v8"
                    it[modelId] = 1
                    it[bodyId] = 1
                    it[transmissionId] = 1
                    it[engineId] = 11182
                    it[cost] = 959900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Comfort v16"
                    it[modelId] = 1
                    it[bodyId] = 1
                    it[transmissionId] = 1
                    it[engineId] = 21127
                    it[cost] = 989900.00
                }
                //Granta Liftback
                EquipmentTable.insert {
                    it[equipmentName] = "Standart"
                    it[modelId] = 1
                    it[bodyId] = 2
                    it[transmissionId] = 1
                    it[engineId] = 11182
                    it[cost] = 754900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Classic"
                    it[modelId] = 1
                    it[bodyId] = 2
                    it[transmissionId] = 1
                    it[engineId] = 11182
                    it[cost] = 880900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Comfort v8"
                    it[modelId] = 1
                    it[bodyId] = 2
                    it[transmissionId] = 1
                    it[engineId] = 11182
                    it[cost] = 989900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Comfort v16"
                    it[modelId] = 1
                    it[bodyId] = 2
                    it[transmissionId] = 1
                    it[engineId] = 21127
                    it[cost] = 1019900.00
                }
                //Granta Cross
                EquipmentTable.insert {
                    it[equipmentName] = "Comfort Light"
                    it[modelId] = 1
                    it[bodyId] = 3
                    it[transmissionId] = 1
                    it[engineId] = 11182
                    it[cost] = 945900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Comfort v8"
                    it[modelId] = 1
                    it[bodyId] = 3
                    it[transmissionId] = 1
                    it[engineId] = 21127
                    it[cost] = 1043900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Comfort v16"
                    it[modelId] = 1
                    it[bodyId] = 3
                    it[transmissionId] = 1
                    it[engineId] = 11182
                    it[cost] = 1073900.00
                }
                //Vesta Sedan
                EquipmentTable.insert {
                    it[equipmentName] = "Comfort v8"
                    it[modelId] = 2
                    it[bodyId] = 4
                    it[transmissionId] = 1
                    it[engineId] = 11182
                    it[cost] = 1239900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Comfort v16"
                    it[modelId] = 2
                    it[bodyId] = 4
                    it[transmissionId] = 1
                    it[engineId] = 21129
                    it[cost] = 1509900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Life"
                    it[modelId] = 2
                    it[bodyId] = 4
                    it[transmissionId] = 1
                    it[engineId] = 21129
                    it[cost] = 1591900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Enjoy"
                    it[modelId] = 2
                    it[bodyId] = 4
                    it[transmissionId] = 1
                    it[engineId] = 21129
                    it[cost] = 1656900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Techno"
                    it[modelId] = 2
                    it[bodyId] = 4
                    it[transmissionId] = 1
                    it[engineId] = 21129
                    it[cost] = 1798900.00
                }
                //Vesta Cross
                EquipmentTable.insert {
                    it[equipmentName] = "Life"
                    it[modelId] = 2
                    it[bodyId] = 5
                    it[transmissionId] = 1
                    it[engineId] = 21129
                    it[cost] = 1733900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Enjoy"
                    it[modelId] = 2
                    it[bodyId] = 5
                    it[transmissionId] = 1
                    it[engineId] = 21129
                    it[cost] = 1801900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Techno"
                    it[modelId] = 2
                    it[bodyId] = 5
                    it[transmissionId] = 1
                    it[engineId] = 21129
                    it[cost] = 1913900.00
                }
                //Vesta SW
                EquipmentTable.insert {
                    it[equipmentName] = "Comfort v8"
                    it[modelId] = 2
                    it[bodyId] = 6
                    it[transmissionId] = 1
                    it[engineId] = 11182
                    it[cost] = 1329900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Comfort v16"
                    it[modelId] = 2
                    it[bodyId] = 6
                    it[transmissionId] = 1
                    it[engineId] = 21129
                    it[cost] = 1639900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Life"
                    it[modelId] = 2
                    it[bodyId] = 6
                    it[transmissionId] = 1
                    it[engineId] = 21129
                    it[cost] = 1721900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Enjoy"
                    it[modelId] = 2
                    it[bodyId] = 6
                    it[transmissionId] = 1
                    it[engineId] = 21129
                    it[cost] = 1786900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Techno"
                    it[modelId] = 2
                    it[bodyId] = 6
                    it[transmissionId] = 1
                    it[engineId] = 21129
                    it[cost] = 1928900.00
                }
                //Vesta SW CROSS
                EquipmentTable.insert {
                    it[equipmentName] = "Comfort"
                    it[modelId] = 2
                    it[bodyId] = 7
                    it[transmissionId] = 1
                    it[engineId] = 11182
                    it[cost] = 1511900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Life"
                    it[modelId] = 2
                    it[bodyId] = 7
                    it[transmissionId] = 1
                    it[engineId] = 21129
                    it[cost] = 1853900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Enjoy"
                    it[modelId] = 2
                    it[bodyId] = 7
                    it[transmissionId] = 1
                    it[engineId] = 21129
                    it[cost] = 1921900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Techno"
                    it[modelId] = 2
                    it[bodyId] = 7
                    it[transmissionId] = 1
                    it[engineId] = 21129
                    it[cost] = 2033900.00
                }
                //Niva Travel SUV
                EquipmentTable.insert {
                    it[equipmentName] = "Classic"
                    it[modelId] = 3
                    it[bodyId] = 8
                    it[transmissionId] = 4
                    it[engineId] = 21129
                    it[cost] = 1217900.00
                }
                EquipmentTable.insert {
                    it[equipmentName] = "Comfort"
                    it[modelId] = 3
                    it[bodyId] = 8
                    it[transmissionId] = 4
                    it[engineId] = 21129
                    it[cost] = 1318900.00
                }
            }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T) = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }

    override suspend fun read(id: Int): Equipment? {
        return dbQuery {
            EquipmentTable
                .select(where = { EquipmentTable.id eq id })
                .singleOrNull()
                ?.let { ResultRowToEquipment(it) }
        }
    }

    override suspend fun readAll(): List<Equipment> {
        return dbQuery {
            EquipmentTable
                .selectAll()
                .map { ResultRowToEquipment(it) }
        }
    }

    override suspend fun readAllBodiesByModel(id: Int): List<Int> {
        return dbQuery {
            EquipmentTable
                .slice(EquipmentTable.bodyId)
                .select { EquipmentTable.modelId eq id }
                .groupBy(EquipmentTable.bodyId)
                .map { it[EquipmentTable.bodyId].value }
        }
    }

    override suspend fun readAllTransmissionsByData(modelId: Int, bodyId: Int): List<Int> {
        return dbQuery {
            EquipmentTable
                .slice(EquipmentTable.transmissionId)
                .select { (EquipmentTable.modelId eq modelId) and
                        (EquipmentTable.bodyId eq bodyId) }
                .groupBy(EquipmentTable.transmissionId)
                .map { it[EquipmentTable.transmissionId].value }
        }
    }

    override suspend fun readAllEnginesByData(modelId: Int, bodyId: Int, transmissionId: Int): List<Short> {
        return dbQuery {
            EquipmentTable
                .slice(EquipmentTable.engineId)
                .select {
                    (EquipmentTable.modelId eq modelId) and
                            (EquipmentTable.bodyId eq bodyId) and
                            (EquipmentTable.transmissionId eq transmissionId)
                }
                .groupBy(EquipmentTable.engineId)
                .map { it[EquipmentTable.engineId] }
        }
    }

    override suspend fun readAllEquipmentsByData(
        modelId: Int,
        bodyId: Int,
        transmissionId: Int,
        engineId: Short
    ): List<Equipment> {
        return dbQuery {
            EquipmentTable
                .select {
                    (EquipmentTable.modelId eq modelId) and
                            (EquipmentTable.bodyId eq bodyId) and
                            (EquipmentTable.transmissionId eq transmissionId) and
                            (EquipmentTable.engineId eq engineId)
                }
                .map { ResultRowToEquipment(it) }
        }
    }

    override suspend fun isEquipmentExists(id: Int): Boolean {
        return dbQuery {
            EquipmentTable
                .select(where = { EquipmentTable.id eq id })
                .count() > 0
        }
    }

    override suspend fun minCostForModel(id: Int): Double? {
        return dbQuery {
            EquipmentTable
                .slice(EquipmentTable.cost.min())
                .select { EquipmentTable.modelId eq id }
                .singleOrNull()
                ?.get(EquipmentTable.cost.min())
                ?.toDouble()
        }
    }

    private fun ResultRowToEquipment(row: ResultRow) = Equipment(
        equipmentId = row[EquipmentTable.id].value,
        name = row[equipmentName],
        modelId = row[modelId].value,
        bodyId = row[bodyId].value,
        transmissionId = row[transmissionId].value,
        engineId = row[engineId],
        cost = row[cost]
    )
}