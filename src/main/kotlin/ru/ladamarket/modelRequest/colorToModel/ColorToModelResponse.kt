package ru.ladamarket.modelRequest.colorToModel

import kotlinx.serialization.Serializable

@Serializable
data class ColorToModelResponse(
    val colorId: Int,
    val colorCode: Short,
    val colorName: String,
    val colorHex: String
)
