package ru.ladamarket.database.services.order

import org.jetbrains.exposed.sql.Database

class OrderServiceImpl(database: Database): OrderService {
    override suspend fun read(id: Int) {
        TODO("Not yet implemented")
    }

}