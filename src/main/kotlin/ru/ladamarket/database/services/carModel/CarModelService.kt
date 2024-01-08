package ru.ladamarket.database.services.carModel

import ru.ladamarket.models.carModels.carModel

interface CarModelService {
    suspend fun read(id: Int): carModel?
    suspend fun readAll(): List<carModel>
}