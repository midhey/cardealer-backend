package ru.ladamarket.database.services.carModel

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ladamarket.database.services.carModel.CarModelServiceImpl.CarModelTable.country
import ru.ladamarket.database.services.carModel.CarModelServiceImpl.CarModelTable.generation
import ru.ladamarket.database.services.carModel.CarModelServiceImpl.CarModelTable.modelName
import ru.ladamarket.database.services.carModel.CarModelServiceImpl.CarModelTable.wheel
import ru.ladamarket.models.carModels.carModel

class CarModelServiceImpl(database: Database): CarModelService {

    object CarModelTable:IntIdTable("car_model") {
        val modelName = varchar("model_name", 25)
        val generation = varchar("generation", 10)
        val country = varchar("country", 30)
        val wheel = varchar("wheel", 15)
    }

    init {
        transaction(database) {
            SchemaUtils.create(CarModelTable)

            CarModelTable.insert {
                it[modelName] = "Granta"
                it[generation] = "FL"
                it[country] = "Россия"
                it[wheel] = "Левый"
            }
            CarModelTable.insert {
                it[modelName] = "Vesta"
                it[generation] = "I Restyling"
                it[country] = "Россия"
                it[wheel] = "Левый"
            }
            CarModelTable.insert {
                it[modelName] = "Niva"
                it[generation] = "Legend"
                it[country] = "Россия"
                it[wheel] = "Левый"
            }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T) = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }
    override suspend fun read(id: Int): carModel? {
        return dbQuery {
            CarModelTable.select(where = {CarModelTable.id eq id}).singleOrNull()?.let {ResultRowToCarModel(it)}
        }
    }

    override suspend fun readAll(): List<carModel> {
        return dbQuery {
            CarModelTable.selectAll().map { ResultRowToCarModel(it) }
        }
    }

    private fun ResultRowToCarModel(row: ResultRow) = carModel(
        modelName = row[modelName],
        generation = row[generation],
        country = row[country],
        wheel = row[wheel]
    )

}