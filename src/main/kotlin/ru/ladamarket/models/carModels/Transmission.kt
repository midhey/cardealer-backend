package ru.ladamarket.models.carModels

import org.jetbrains.exposed.dao.id.EntityID

data class Transmission(
    val transmissionId: EntityID<Int>,
    val type: String,
    val drive: String,
    val transmissionCount: Short
)