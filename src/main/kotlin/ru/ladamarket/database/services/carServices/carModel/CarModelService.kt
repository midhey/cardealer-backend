package ru.ladamarket.database.services.carServices.carModel

import ru.ladamarket.models.carModels.CarModel

interface CarModelService {
    suspend fun read(id: Int): CarModel?
    suspend fun readAll(): List<CarModel>
    suspend fun isModelExists(id: Int): Boolean
}