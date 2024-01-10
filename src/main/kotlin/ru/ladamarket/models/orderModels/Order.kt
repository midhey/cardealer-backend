package ru.ladamarket.models.orderModels

data class Order(
    val modelId: Short,
    val colorId: Short,
    val userId: Int,
    val status: String,
    val totalCost: Float,
    val orderTime: String
)
