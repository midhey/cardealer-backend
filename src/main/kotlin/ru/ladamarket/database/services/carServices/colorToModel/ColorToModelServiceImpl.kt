package ru.ladamarket.database.services.carServices.colorToModel

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ladamarket.database.services.carServices.carModel.CarModelServiceImpl
import ru.ladamarket.database.services.carServices.colorToModel.ColorToModelServiceImpl.ColorToModelTable.colorCode
import ru.ladamarket.database.services.carServices.colorToModel.ColorToModelServiceImpl.ColorToModelTable.modelId
import ru.ladamarket.database.services.carServices.equipment.EquipmentServiceImpl
import ru.ladamarket.database.services.color.ColorServiceImpl
import ru.ladamarket.models.carModels.ColorToModel

class ColorToModelServiceImpl(database: Database): ColorToModelService {

    object ColorToModelTable:IntIdTable("color_to_model") {
        val modelId = reference("model_id", CarModelServiceImpl.CarModelTable)
        val colorCode = reference("color_code", ColorServiceImpl.ColorTable.colorCode)
    }

    init {
        transaction(database) {
            SchemaUtils.create(ColorToModelTable)

            if (ColorToModelTable.selectAll().count().toInt() == 0) {
                ColorToModelTable.insert {
                    it[modelId] = 3
                    it[colorCode] = 115
                }
                ColorToModelTable.insert {
                    it[modelId] = 2
                    it[colorCode] = 130
                }
                ColorToModelTable.insert {
                    it[modelId] = 1
                    it[colorCode] = 195
                }
                ColorToModelTable.insert {
                    it[modelId] = 1
                    it[colorCode] = 221
                }
                ColorToModelTable.insert {
                    it[modelId] = 2
                    it[colorCode] = 221
                }
                ColorToModelTable.insert {
                    it[modelId] = 2
                    it[colorCode] = 246
                }
                ColorToModelTable.insert {
                    it[modelId] = 1
                    it[colorCode] = 418
                }
                ColorToModelTable.insert {
                    it[modelId] = 2
                    it[colorCode] = 476
                }
                ColorToModelTable.insert {
                    it[modelId] = 1
                    it[colorCode] = 676
                }
                ColorToModelTable.insert {
                    it[modelId] = 2
                    it[colorCode] = 676
                }
            }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T) = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }
    override suspend fun read(id: Int): ColorToModel? {
        return dbQuery {
            ColorToModelTable
                .select(where = {ColorToModelTable.modelId eq id})
                .singleOrNull()
                ?.let { ResultRowToColorToModel(it) }
        }
    }

    override suspend fun readAllByModel(id: Int): List<Short> {
        return dbQuery {
            ColorToModelTable
                .slice(ColorToModelTable.colorCode)
                .select(where = {ColorToModelTable.modelId eq id})
                .map { it[ColorToModelTable.colorCode] }
        }
    }

    private fun ResultRowToColorToModel(row: ResultRow) = ColorToModel(
        colorId = row[ColorToModelTable.id].value,
        modelId = row[modelId].value,
        colorCode = row[colorCode]
    )
}