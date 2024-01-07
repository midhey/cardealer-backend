package ru.ladamarket.modelRequest.color

import kotlinx.serialization.Serializable

@Serializable
data class colorRequest(
    val colorName: String,
    val colorCode: Short,
    val colorHex: String
)

