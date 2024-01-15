package ru.ladamarket.database.services.order

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.ladamarket.database.services.carServices.colorToModel.ColorToModelServiceImpl
import ru.ladamarket.database.services.carServices.equipment.EquipmentServiceImpl
import ru.ladamarket.database.services.order.OrderServiceImpl.OrderTable.colorId
import ru.ladamarket.database.services.order.OrderServiceImpl.OrderTable.equipmentId
import ru.ladamarket.database.services.order.OrderServiceImpl.OrderTable.status
import ru.ladamarket.database.services.order.OrderServiceImpl.OrderTable.timestamp
import ru.ladamarket.database.services.order.OrderServiceImpl.OrderTable.userId
import ru.ladamarket.database.services.user.UserServiceImpl
import ru.ladamarket.modelRequest.order.OrderRequest
import ru.ladamarket.modelRequest.order.OrderResponse
import ru.ladamarket.models.orderModels.Order
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class OrderServiceImpl(database: Database): OrderService {

    object OrderTable:IntIdTable("order") {
        val equipmentId = reference("equipment_id", EquipmentServiceImpl.EquipmentTable)
        val colorId = reference("color_id", ColorToModelServiceImpl.ColorToModelTable)
        val userId = reference("user_id", UserServiceImpl.UserTable)
        val status = varchar("status", 15).default("Ожидает")
        val timestamp = date("timestamp")
    }

    init {
        transaction(database) {
            SchemaUtils.create(OrderTable)
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T) = newSuspendedTransaction(Dispatchers.IO) {
        block()
    }

    override suspend fun read(id: Int): Order? {
        return dbQuery {
            OrderTable.select(
                where = {OrderTable.id eq id}
            )
                .singleOrNull()
                ?.let { ResultRowToOrder(it) }
        }
    }

    override suspend fun readAll(): List<Order> {
        return dbQuery {
            OrderTable.selectAll()
                .map { ResultRowToOrder(it) }
        }
    }

    override suspend fun readAllOrdersByUser(id: Int): List<Order> {
        return dbQuery {
            OrderTable.select(where = {OrderTable.userId eq id})
                .map { ResultRowToOrder(it) }
        }
    }

    override suspend fun create(order: OrderRequest, id: Int) {
        dbQuery {
            OrderTable.insert {
                it[equipmentId] = order.equipmentId
                it[colorId] = order.colorId
                it[userId] = id
                it[status] = "Ожидает"
                it[timestamp] = LocalDate.now()
            }
        }
    }

    override suspend fun setStatus(id: Int, status: String) {
        dbQuery {
            OrderTable.update({ OrderTable.id eq id }) {
                it[this.status] = status
            }
        }
    }

    private fun ResultRowToOrder(row: ResultRow) = Order(
        orderId = row[OrderTable.id].value,
        equipmentId = row[equipmentId].value,
        colorId = row[colorId].value,
        userId = row[userId].value,
        status = row[status],
        orderTime = row[timestamp]
    )
}