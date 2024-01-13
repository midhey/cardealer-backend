package ru.ladamarket.database.services.order

interface OrderService {
    suspend fun read(id: Int)
}