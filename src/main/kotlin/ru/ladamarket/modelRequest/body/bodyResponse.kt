package ru.ladamarket.modelRequest.body

data class bodyResponse(
    val name: String,
    val length: Short,
    val width: Short,
    val height: Short,
    val wheelbase: Short,
    val clearance: Short,
    val seatsCount: Short,
    val trunkVolume: Short,
    val tankVolume: Short,
    val weight: Short,
    val maxWeight: Short
)
