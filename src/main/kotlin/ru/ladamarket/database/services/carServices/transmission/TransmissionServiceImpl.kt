package ru.ladamarket.database.services.carServices.transmission

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ladamarket.database.services.carServices.transmission.TransmissionServiceImpl.TransmissionTable.transmissionCount
import ru.ladamarket.database.services.carServices.transmission.TransmissionServiceImpl.TransmissionTable.transmissionDrive
import ru.ladamarket.database.services.carServices.transmission.TransmissionServiceImpl.TransmissionTable.transmissionType
import ru.ladamarket.models.carModels.Transmission

class TransmissionServiceImpl(database: Database): TransmissionService {

    object TransmissionTable:IntIdTable("transmission") {
        val transmissionType = varchar("type",10)
        val transmissionDrive = varchar("drive", 10)
        val transmissionCount = short("transmission_count")
    }

    init {
        transaction(database) {
            SchemaUtils.create(TransmissionTable)

            if (TransmissionTable.select(where = { TransmissionTable.transmissionType eq "Механика"}).count().toInt() == 0) {
                TransmissionTable.insert {
                    it[transmissionType] = "Механика"
                    it[transmissionDrive] = "Передний"
                    it[transmissionCount] = 5
                }
                TransmissionTable.insert {
                    it[transmissionType] = "Робот"
                    it[transmissionDrive] = "Передний"
                    it[transmissionCount] = 5
                }
                TransmissionTable.insert {
                    it[transmissionType] = "Механика"
                    it[transmissionDrive] = "Передний"
                    it[transmissionCount] = 4
                }
                TransmissionTable.insert {
                    it[transmissionType] = "Механика"
                    it[transmissionDrive] = "Полный"
                    it[transmissionCount] = 5
                }
            }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T) = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }
    override suspend fun read(id: Int): Transmission? {
        return dbQuery {
            TransmissionTable.select(where = { TransmissionTable.id eq id}).singleOrNull()?.let {ResultRowToTransmission(it)}
        }
    }

    override suspend fun readAll(): List<Transmission> {
        return dbQuery {
            TransmissionTable.selectAll().map { ResultRowToTransmission(it) }
        }
    }

    private fun ResultRowToTransmission(row: ResultRow) = Transmission(
        type = row[transmissionType],
        drive = row[transmissionDrive],
        transmissionCount = row[transmissionCount]
    )
}