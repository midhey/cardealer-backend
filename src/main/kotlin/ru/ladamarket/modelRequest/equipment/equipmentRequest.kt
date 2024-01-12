package ru.ladamarket.modelRequest.equipment

import kotlinx.serialization.Serializable

@Serializable
data class equipmentRequest(
    val id: Int?,
    val name: String,
    val bodyId: Short,
    val transmissionId: Short,
    val engineId: Short
)
