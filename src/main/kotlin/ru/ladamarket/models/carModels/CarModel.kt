package ru.ladamarket.models.carModels

data class CarModel (
    val modelId: Int,
    val modelName: String,
    val generation: String,
    val country: String,
    val wheel: String
)
