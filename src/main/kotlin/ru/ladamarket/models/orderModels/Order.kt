package ru.ladamarket.models.orderModels

import java.time.LocalDate

data class Order(
    val orderId: Int,
    val equipmentId: Int,
    val colorId: Int,
    val userId: Int,
    val status: String,
    val orderTime: LocalDate
)
