package ru.ladamarket.database.services.body

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ladamarket.database.services.body.bodyServiceImpl.BodyTable.bodyName
import ru.ladamarket.database.services.body.bodyServiceImpl.BodyTable.clearance
import ru.ladamarket.database.services.body.bodyServiceImpl.BodyTable.height
import ru.ladamarket.database.services.body.bodyServiceImpl.BodyTable.length
import ru.ladamarket.database.services.body.bodyServiceImpl.BodyTable.maxWeight
import ru.ladamarket.database.services.body.bodyServiceImpl.BodyTable.seatsCount
import ru.ladamarket.database.services.body.bodyServiceImpl.BodyTable.tankVolume
import ru.ladamarket.database.services.body.bodyServiceImpl.BodyTable.trunkVolume
import ru.ladamarket.database.services.body.bodyServiceImpl.BodyTable.weight
import ru.ladamarket.database.services.body.bodyServiceImpl.BodyTable.wheelbase
import ru.ladamarket.database.services.body.bodyServiceImpl.BodyTable.width
import ru.ladamarket.models.carModels.Body

class bodyServiceImpl(database: Database): bodyService {

    object BodyTable:IntIdTable("body") {
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
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T) = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }
    override suspend fun read(id: Int): Body? {
        return dbQuery {
            BodyTable.select(where = {BodyTable.id eq id}).singleOrNull()?.let { ResultRowToBody(it) }
        }
    }

    override suspend fun readAll(): List<Body> {
        return dbQuery {
            BodyTable.selectAll().map { ResultRowToBody(it) }
        }
    }

    override suspend fun isBodyExist(id: Int): Boolean {
        return dbQuery {
            BodyTable.select(where = {BodyTable.id eq id}).count()>0
        }
    }

    private fun ResultRowToBody(row: ResultRow) = Body(
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