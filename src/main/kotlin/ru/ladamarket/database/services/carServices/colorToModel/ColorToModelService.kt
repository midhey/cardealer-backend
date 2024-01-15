package ru.ladamarket.database.services.carServices.colorToModel

import ru.ladamarket.models.carModels.ColorToModel

interface ColorToModelService {
    suspend fun read(id: Int): ColorToModel?
    suspend fun readAllByModel(id: Int): List<Short>
}