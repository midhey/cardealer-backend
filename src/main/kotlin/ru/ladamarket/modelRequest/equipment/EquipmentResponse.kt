package ru.ladamarket.modelRequest.equipment

import kotlinx.serialization.Serializable

@Serializable
data class EquipmentResponse(
    val equipmentId: Int,
    val equipmentName: String,
    val modelId: Int,
    val modelName: String,
    val generation: String,
    val country: String,
    val wheel: String,
    val bodyId: Int,
    val bodyName: String,
    val length: Short,
    val width: Short,
    val height: Short,
    val wheelbase: Short,
    val clearance: Short,
    val seatsCount: Short,
    val trunkVolume: Short,
    val tankVolume: Short,
    val weight: Short,
    val maxWeight: Short,
    val transmissionId: Int,
    val transmissionType: String,
    val drive: String,
    val transmissionCount: Short,
    val engineId: Short,
    val engineType: String,
    val cylindersCount: Short,
    val valveCount: Short,
    val displacement: Short,
    val fuelType: String,
    val power: Short,
    val torque: Short,
    val ecoClass: String
)
