package ru.ladamarket.modelRequest.colorToModel

import kotlinx.serialization.Serializable

@Serializable
data class ColorToModelResponse(
    val colorId: Int,
    val modelId: Int,
    val colorCode: Short
)
