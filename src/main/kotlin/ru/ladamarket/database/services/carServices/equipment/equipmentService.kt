package ru.ladamarket.database.services.carServices.equipment

import ru.ladamarket.models.carModels.Equipment

interface equipmentService {
    suspend fun read(id: Short): Equipment
    suspend fun readAll(): List<Equipment>
}