package ru.ladamarket.database.services.carServices.equipment

import ru.ladamarket.models.carModels.Equipment

interface EquipmentService {
    suspend fun read(id: Int): Equipment?
    suspend fun readAll(): List<Equipment>
    suspend fun readAllBodiesByModel(id: Int): List<Int>
    suspend fun isEquipmentExists(id: Int): Boolean
}