package ru.ladamarket.database.services.color

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ladamarket.database.services.color.ColorServiceImpl.ColorTable.colorCode
import ru.ladamarket.database.services.color.ColorServiceImpl.ColorTable.colorHex
import ru.ladamarket.database.services.color.ColorServiceImpl.ColorTable.colorName
import ru.ladamarket.modelRequest.color.colorRequest
import ru.ladamarket.models.color.Color

class ColorServiceImpl(database: Database): ColorService {

    object ColorTable: Table("colors") {
        val colorName = ColorTable.varchar("color_name", 30)
        val colorCode = ColorTable.short("color_code")
        val colorHex = ColorTable.char("color_hex", 6)

        override val primaryKey = PrimaryKey(colorCode, name = "PK_ColorTable_Code")
    }

    init {
        transaction(database) {
            SchemaUtils.create(ColorTable)


            if (ColorTable.select { ColorTable.colorCode eq 115 }.count().toInt() == 0) {
                ColorTable.insert {
                    it[colorName] = "Феерия"
                    it[colorCode] = 115
                    it[colorHex] = "96011D"
                }
                ColorTable.insert {
                    it[colorName] = "Марс"
                    it[colorCode] = 130
                    it[colorHex] = "CB390A"
                }
                ColorTable.insert {
                    it[colorName] = "Ледниковый"
                    it[colorCode] = 221
                    it[colorHex] = "FBFAF8"
                }
                ColorTable.insert {
                    it[colorName] = "Сердолик"
                    it[colorCode] = 195
                    it[colorHex] = "811717"
                }
                ColorTable.insert {
                    it[colorName] = "Ангкор"
                    it[colorCode] = 246
                    it[colorHex] = "2C2417"
                }
                ColorTable.insert {
                    it[colorName] = "Голубая планета"
                    it[colorCode] = 418
                    it[colorHex] = "0A0EF2"
                }
                ColorTable.insert {
                    it[colorName] = "Дайвинг"
                    it[colorCode] = 476
                    it[colorHex] = "1449BF"
                }
                ColorTable.insert {
                    it[colorName] = "Черная жемчужина"
                    it[colorCode] = 676
                    it[colorHex] = "141414"
                }
            }
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T) = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }
    override suspend fun colorAdd(request: colorRequest) {
        dbQuery {
            ColorTable.insert {
                it[colorName] = request.colorName
                it[colorCode] = request.colorCode
                it[colorHex] = request.colorHex
            }
        }
    }

    override suspend fun colorDelete(code: Short) {
        dbQuery {
            ColorTable.deleteWhere { ColorTable.colorCode eq code }
        }
    }

    override suspend fun colorUpdate(request: colorRequest) {
        dbQuery {
            ColorTable.update(where = {ColorTable.colorCode eq request.colorCode}) {
                it[colorName] = request.colorName
            }
        }
    }

    override suspend fun read(code: Short):Color? {
        return dbQuery {
            ColorTable.select { ColorTable.colorCode eq code }.singleOrNull()?.let {ResultRowToColor(it)}
        }
    }

    override suspend fun readAll(): List<Color> {
        return dbQuery {
            ColorTable.selectAll().map { ResultRowToColor(it) }
        }
    }

    override suspend fun isColorExist(code: Short):Boolean {
        return dbQuery {
            ColorTable.select(where = {ColorTable.colorCode eq code}).count() > 0
        }
    }

    private fun ResultRowToColor(row: ResultRow) = Color(
        colorName = row[colorName],
        colorCode = row[colorCode],
        colorHex = row[colorHex]
    )
}