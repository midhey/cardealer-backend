package ru.ladamarket.modelRequest.transmission

import kotlinx.serialization.Serializable

@Serializable
data class TransmissionResponse(
    val transmissionId: Int,
    val type: String,
    val drive: String,
    val transmissionCount: Short
)
