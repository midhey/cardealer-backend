package ru.ladamarket.modelRequest.carModel

import kotlinx.serialization.Serializable

@Serializable
data class carModelResponse(
    val carModelId: Int,
    val modelName: String,
    val generation: String,
    val country: String,
    val wheel: String
)
