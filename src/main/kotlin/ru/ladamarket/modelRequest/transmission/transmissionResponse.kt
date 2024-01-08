package ru.ladamarket.modelRequest.transmission

import kotlinx.serialization.Serializable

@Serializable
data class transmissionResponse(
    val type: String,
    val drive: String,
    val transmissionCount: Short
)
