package ru.ladamarket.database.services.carServices.carModel

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ladamarket.database.services.carServices.carModel.CarModelServiceImpl.CarModelTable.country
import ru.ladamarket.database.services.carServices.carModel.CarModelServiceImpl.CarModelTable.generation
import ru.ladamarket.database.services.carServices.carModel.CarModelServiceImpl.CarModelTable.modelName
import ru.ladamarket.database.services.carServices.carModel.CarModelServiceImpl.CarModelTable.wheel
import ru.ladamarket.models.carModels.CarModel

class CarModelServiceImpl(database: Database): CarModelService {

    object CarModelTable: IntIdTable("car_model") {
        val modelName = varchar("model_name", 25)
        val generation = varchar("generation", 30)
        val country = varchar("country", 30)
        val wheel = varchar("wheel", 15)
    }

    init {
        transaction(database) {
            SchemaUtils.create(CarModelTable)

            if(CarModelTable.select(where = {CarModelTable.modelName eq "Granta"}).count().toInt()==0) {
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
    }

    private suspend fun <T> dbQuery(block: suspend () -> T) = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }
    override suspend fun read(id: Int): CarModel? {
        return dbQuery {
            CarModelTable.select(where = { CarModelTable.id eq id}).singleOrNull()?.let {ResultRowToCarModel(it)}
        }
    }

    override suspend fun readAll(): List<CarModel> {
        return dbQuery {
            CarModelTable.selectAll().map { ResultRowToCarModel(it) }
        }
    }

    override suspend fun isModelExists(id: Int): Boolean {
        return dbQuery {
            CarModelTable.select(where = {CarModelTable.id eq id}).count() > 0
        }
    }

    private fun ResultRowToCarModel(row: ResultRow) = CarModel(
        modelId = row[CarModelTable.id].value,
        modelName = row[modelName],
        generation = row[generation],
        country = row[country],
        wheel = row[wheel]
    )

}