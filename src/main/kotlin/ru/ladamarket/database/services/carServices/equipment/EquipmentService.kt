package ru.ladamarket.database.services.carServices.equipment

import ru.ladamarket.models.carModels.Equipment

interface EquipmentService {
    suspend fun read(id: Int): Equipment?
    suspend fun readAll(): List<Equipment>
    suspend fun readAllBodiesByModel(id: Int): List<Int>
    suspend fun readAllTransmissionsByData(modelId: Int, bodyId: Int): List<Int>
    suspend fun readAllEnginesByData(modelId: Int, bodyId: Int, transmissionId: Int): List<Short>
    suspend fun readAllEquipmentsByData(modelId: Int, bodyId: Int, transmissionId: Int, engineId: Short): List<Equipment>
    suspend fun isEquipmentExists(id: Int): Boolean
}