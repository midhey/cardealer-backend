package ru.ladamarket.modelRequest.equipment

import kotlinx.serialization.Serializable

@Serializable
data class EquipmentRequest(
    val equipmentId: Int,
    val name: String,
    val modelId: Int,
    val bodyId: Int,
    val transmissionId: Int,
    val engineId: Int,
    val cost: Double
)
