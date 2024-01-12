package ru.ladamarket.database.services.carServices.engine

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ladamarket.database.services.carServices.engine.EngineServiceImpl.EngineTable.cylindersCount
import ru.ladamarket.database.services.carServices.engine.EngineServiceImpl.EngineTable.displacement
import ru.ladamarket.database.services.carServices.engine.EngineServiceImpl.EngineTable.ecoClass
import ru.ladamarket.database.services.carServices.engine.EngineServiceImpl.EngineTable.engineId
import ru.ladamarket.database.services.carServices.engine.EngineServiceImpl.EngineTable.fuelType
import ru.ladamarket.database.services.carServices.engine.EngineServiceImpl.EngineTable.power
import ru.ladamarket.database.services.carServices.engine.EngineServiceImpl.EngineTable.torque
import ru.ladamarket.database.services.carServices.engine.EngineServiceImpl.EngineTable.type
import ru.ladamarket.database.services.carServices.engine.EngineServiceImpl.EngineTable.valveCount
import ru.ladamarket.models.carModels.Engine

class EngineServiceImpl(database: Database): EngineService {

    object EngineTable:Table("engine") {
        val engineId = short("engine_id")
        val type = varchar("engine_type", 20)
        val cylindersCount = short("cylinders_count")
        val valveCount = short("valve_count")
        val displacement = short("displacement")
        val fuelType = varchar("fuel_type", 10)
        val power = short("power")
        val torque = short("torque")
        val ecoClass = varchar("eco_class", 10)

        override val primaryKey = PrimaryKey(engineId, name = "PK_EngineTable_Code")
    }

    init {
        transaction(database) {
            SchemaUtils.create(EngineTable)

            if (EngineTable.select { EngineTable.engineId eq 11182}.count().toInt() == 0) {
                //1.6 8v
                EngineTable.insert {
                    it[engineId] = 11182
                    it[type] = "Рядный"
                    it[cylindersCount] = 4
                    it[valveCount] = 8
                    it[displacement] = 1596
                    it[fuelType] = "АИ-92"
                    it[power] = 90
                    it[torque] = 143
                    it[ecoClass] = "ЕВРО-5"
                }
                EngineTable.insert {
                    it[engineId] = 11183
                    it[type] = "Рядный"
                    it[cylindersCount] = 4
                    it[valveCount] = 8
                    it[displacement] = 1596
                    it[fuelType] = "АИ-92"
                    it[power] = 80
                    it[torque] = 120
                    it[ecoClass] = "ЕВРО-2/3"
                }
                EngineTable.insert {
                    it[engineId] = 11186
                    it[type] = "Рядный"
                    it[cylindersCount] = 4
                    it[valveCount] = 8
                    it[displacement] = 1596
                    it[fuelType] = "АИ-92"
                    it[power] = 87
                    it[torque] = 140
                    it[ecoClass] = "ЕВРО-4"
                }
                EngineTable.insert {
                    it[engineId] = 11189
                    it[type] = "Рядный"
                    it[cylindersCount] = 4
                    it[valveCount] = 8
                    it[displacement] = 1596
                    it[fuelType] = "АИ-92"
                    it[power] = 87
                    it[torque] = 140
                    it[ecoClass] = "ЕВРО-5"
                }
                EngineTable.insert {
                    it[engineId] = 21114
                    it[type] = "Рядный"
                    it[cylindersCount] = 4
                    it[valveCount] = 8
                    it[displacement] = 1596
                    it[fuelType] = "АИ-92"
                    it[power] = 80
                    it[torque] = 120
                    it[ecoClass] = "ЕВРО-2/3"
                }
                EngineTable.insert {
                    it[engineId] = 21116
                    it[type] = "Рядный"
                    it[cylindersCount] = 4
                    it[valveCount] = 8
                    it[displacement] = 1596
                    it[fuelType] = "АИ-92"
                    it[power] = 87
                    it[torque] = 140
                    it[ecoClass] = "ЕВРО-4"
                }
                //1.4 16v
                EngineTable.insert {
                    it[engineId] = 11194
                    it[type] = "Рядный"
                    it[cylindersCount] = 4
                    it[valveCount] = 16
                    it[displacement] = 1390
                    it[fuelType] = "АИ-92"
                    it[power] = 89
                    it[torque] = 127
                    it[ecoClass] = "ЕВРО-3/4"
                }
                //1.6 16v
                EngineTable.insert {
                    it[engineId] = 21124
                    it[type] = "Рядный"
                    it[cylindersCount] = 4
                    it[valveCount] = 16
                    it[displacement] = 1599
                    it[fuelType] = "АИ-92"
                    it[power] = 89
                    it[torque] = 133
                    it[ecoClass] = "ЕВРО-2/3"
                }
                EngineTable.insert {
                    it[engineId] = 21126
                    it[type] = "Рядный"
                    it[cylindersCount] = 4
                    it[valveCount] = 16
                    it[displacement] = 1597
                    it[fuelType] = "АИ-92"
                    it[power] = 98
                    it[torque] = 145
                    it[ecoClass] = "ЕВРО-3/4"
                }
                EngineTable.insert {
                    it[engineId] = 21127
                    it[type] = "Рядный"
                    it[cylindersCount] = 4
                    it[valveCount] = 16
                    it[displacement] = 1596
                    it[fuelType] = "АИ-92"
                    it[power] = 106
                    it[torque] = 148
                    it[ecoClass] = "ЕВРО-4"
                }
                EngineTable.insert {
                    it[engineId] = 21129
                    it[type] = "Рядный"
                    it[cylindersCount] = 4
                    it[valveCount] = 16
                    it[displacement] = 1596
                    it[fuelType] = "АИ-92"
                    it[power] = 106
                    it[torque] = 148
                    it[ecoClass] = "ЕВРО-5"
                }
                //1.8 16v
                EngineTable.insert {
                    it[engineId] = 21128
                    it[type] = "Рядный"
                    it[cylindersCount] = 4
                    it[valveCount] = 16
                    it[displacement] = 1774
                    it[fuelType] = "АИ-92"
                    it[power] = 123
                    it[torque] = 165
                    it[ecoClass] = "ЕВРО-4"
                }
                EngineTable.insert {
                    it[engineId] = 21179
                    it[type] = "Рядный"
                    it[cylindersCount] = 4
                    it[valveCount] = 16
                    it[displacement] = 1774
                    it[fuelType] = "АИ-92"
                    it[power] = 145
                    it[torque] = 184
                    it[ecoClass] = "ЕВРО-5"
                }
            }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T) = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }
    override suspend fun read(id: Short): Engine? {
        return dbQuery {
            EngineTable.select(where = { EngineTable.engineId eq id}).singleOrNull()?.let { ResultRowToEngine(it) }
        }
    }

    override suspend fun readAll(): List<Engine> {
        return dbQuery {
            EngineTable.selectAll().map { ResultRowToEngine(it) }
        }
    }

    override suspend fun isEngineExist(id: Short): Boolean {
        return dbQuery {
            EngineTable.select(where = { EngineTable.engineId eq id}).count()>0
        }
    }

    private fun ResultRowToEngine(row: ResultRow) = Engine (
        engineId = row[engineId],
        type = row[type],
        cylindersCount = row[cylindersCount],
        valveCount = row[valveCount],
        displacement = row[displacement],
        fuelType = row[fuelType],
        power = row[power],
        torque = row[torque],
        ecoClass = row[ecoClass]
    )
}