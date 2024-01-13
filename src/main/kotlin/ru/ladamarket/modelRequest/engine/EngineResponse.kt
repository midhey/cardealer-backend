package ru.ladamarket.modelRequest.engine

import kotlinx.serialization.Serializable

@Serializable
data class EngineResponse(
    val engineId: Short,
    val type: String,
    val cylindersCount: Short,
    val valveCount: Short,
    val displacement: Short,
    val fuelType: String,
    val power: Short,
    val torque: Short,
    val ecoClass: String
)
