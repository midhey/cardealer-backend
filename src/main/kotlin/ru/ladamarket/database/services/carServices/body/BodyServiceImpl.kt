package ru.ladamarket.database.services.carServices.body

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ladamarket.database.services.carServices.body.BodyServiceImpl.BodyTable.bodyName
import ru.ladamarket.database.services.carServices.body.BodyServiceImpl.BodyTable.clearance
import ru.ladamarket.database.services.carServices.body.BodyServiceImpl.BodyTable.height
import ru.ladamarket.database.services.carServices.body.BodyServiceImpl.BodyTable.length
import ru.ladamarket.database.services.carServices.body.BodyServiceImpl.BodyTable.maxWeight
import ru.ladamarket.database.services.carServices.body.BodyServiceImpl.BodyTable.seatsCount
import ru.ladamarket.database.services.carServices.body.BodyServiceImpl.BodyTable.tankVolume
import ru.ladamarket.database.services.carServices.body.BodyServiceImpl.BodyTable.trunkVolume
import ru.ladamarket.database.services.carServices.body.BodyServiceImpl.BodyTable.weight
import ru.ladamarket.database.services.carServices.body.BodyServiceImpl.BodyTable.wheelbase
import ru.ladamarket.database.services.carServices.body.BodyServiceImpl.BodyTable.width
import ru.ladamarket.models.carModels.Body


class BodyServiceImpl(database: Database): BodyService {

    object BodyTable: IntIdTable("body") {
        val bodyName = varchar("name", 15)
        val length = short("length")
        val width = short("width")
        val height = short("height")
        val wheelbase = short("wheelbase")
        val clearance = short("clearance")
        val seatsCount = short("seats_count")
        val trunkVolume = short("trunk_volume")
        val tankVolume = short("tank_volume")
        val weight = short("weight")
        val maxWeight = short("max_weight")
    }

    init {
        transaction(database) {
            SchemaUtils.create(BodyTable)

            if (BodyTable.select(where = {BodyTable.bodyName eq "Седан"}).count().toInt()==0) {
                //Granta
                BodyTable.insert {
                    it[bodyName] = "Седан"
                    it[length] = 4268
                    it[width] = 1700
                    it[height] = 1500
                    it[wheelbase] = 2476
                    it[clearance] = 180
                    it[seatsCount] = 5
                    it[trunkVolume] = 520
                    it[tankVolume] = 50
                    it[weight] = 1075
                    it[maxWeight] = 1560
                }
                BodyTable.insert {
                    it[bodyName] = "Лифтбек"
                    it[length] = 4250
                    it[width] = 1700
                    it[height] = 1500
                    it[wheelbase] = 2476
                    it[clearance] = 180
                    it[seatsCount] = 5
                    it[trunkVolume] = 440
                    it[tankVolume] = 50
                    it[weight] = 1085
                    it[maxWeight] = 1560
                }
                BodyTable.insert {
                    it[bodyName] = "CROSS"
                    it[length] = 4148
                    it[width] = 1700
                    it[height] = 1560
                    it[wheelbase] = 2476
                    it[clearance] = 198
                    it[seatsCount] = 5
                    it[trunkVolume] = 355
                    it[tankVolume] = 50
                    it[weight] = 1125
                    it[maxWeight] = 1560
                }
                //Vesta
                BodyTable.insert {
                    it[bodyName] = "Седан"
                    it[length] = 4400
                    it[width] = 1764
                    it[height] = 1496
                    it[wheelbase] = 2635
                    it[clearance] = 178
                    it[seatsCount] = 5
                    it[trunkVolume] = 480
                    it[tankVolume] = 55
                    it[weight] = 1220
                    it[maxWeight] = 1730
                }
                BodyTable.insert {
                    it[bodyName] = "CROSS"
                    it[length] = 4445
                    it[width] = 1785
                    it[height] = 1522
                    it[wheelbase] = 2635
                    it[clearance] = 203
                    it[seatsCount] = 5
                    it[trunkVolume] = 480
                    it[tankVolume] = 55
                    it[weight] = 1220
                    it[maxWeight] = 1730
                }
                BodyTable.insert {
                    it[bodyName] = "SW"
                    it[length] = 4440
                    it[width] = 1764
                    it[height] = 1500
                    it[wheelbase] = 2635
                    it[clearance] = 178
                    it[seatsCount] = 5
                    it[trunkVolume] = 480
                    it[tankVolume] = 55
                    it[weight] = 1220
                    it[maxWeight] = 1730
                }
                BodyTable.insert {
                    it[bodyName] = "SW CROSS"
                    it[length] = 4445
                    it[width] = 1785
                    it[height] = 1522
                    it[wheelbase] = 2635
                    it[clearance] = 203
                    it[seatsCount] = 5
                    it[trunkVolume] = 480
                    it[tankVolume] = 55
                    it[weight] = 1275
                    it[maxWeight] = 1730
                }
                //Niva
                BodyTable.insert {
                    it[bodyName] = "SUV"
                    it[length] = 4099
                    it[width] = 1804
                    it[height] = 1690
                    it[wheelbase] = 2450
                    it[clearance] = 220
                    it[seatsCount] = 5
                    it[trunkVolume] = 320
                    it[tankVolume] = 58
                    it[weight] = 1485
                    it[maxWeight] = 1860
                }
            }

        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T) = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }
    override suspend fun read(id: Int): Body? {
        return dbQuery {
            BodyTable.select(where = { BodyTable.id eq id}).singleOrNull()?.let { ResultRowToBody(it) }
        }
    }

    override suspend fun readAll(): List<Body> {
        return dbQuery {
            BodyTable.selectAll().map { ResultRowToBody(it) }
        }
    }

    override suspend fun isBodyExist(id: Int): Boolean {
        return dbQuery {
            BodyTable.select(where = { BodyTable.id eq id}).count()>0
        }
    }

    private fun ResultRowToBody(row: ResultRow) = Body(
        bodyId = row[BodyTable.id],
        name = row[bodyName],
        length = row[length],
        width = row[width],
        height = row[height],
        wheelbase = row[wheelbase],
        clearance = row[clearance],
        seatsCount = row[seatsCount],
        trunkVolume = row[trunkVolume],
        tankVolume = row[tankVolume],
        weight = row[weight],
        maxWeight = row[maxWeight]
    )
}