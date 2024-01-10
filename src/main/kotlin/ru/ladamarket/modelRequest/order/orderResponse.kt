package ru.ladamarket.modelRequest.order

import kotlinx.serialization.Serializable

@Serializable
data class orderResponse(
    val modelId: Short,
    val colorId: Short,
    val userId: Int,
    val status: String,
    val totalCost: Float,
    val orderTime: String
)
