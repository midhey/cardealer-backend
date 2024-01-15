package ru.ladamarket.database.services.order

import ru.ladamarket.modelRequest.order.OrderRequest
import ru.ladamarket.models.orderModels.Order

interface OrderService {
    suspend fun read(id: Int): Order?
    suspend fun readAll(): List<Order>
    suspend fun readAllOrdersByUser(id: Int): List<Order>
    suspend fun create(order: OrderRequest, id: Int)
    suspend fun setStatus(id: Int, status: String)
}