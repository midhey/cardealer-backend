package ru.ladamarket.modelRequest.carModel

import kotlinx.serialization.Serializable

@Serializable
data class CarModelResponse(
    val carModelId: Int,
    val modelName: String,
    val generation: String,
    val country: String,
    val wheel: String,
    val cost: Int
)
