package ru.ladamarket.modelRequest.order

import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    val orderId: Int,
    val modelName: String,
    val bodyName: String,
    val equipmentId: Int,
    val equipmentName: String,
    val colorName: String,
    val colorHex: String,
    val userId: Int,
    val userPhone: String,
    val userMail: String,
    val status: String,
    val orderTime: String
)
